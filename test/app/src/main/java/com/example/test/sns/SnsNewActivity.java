package com.example.test.sns;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SnsNewActivity extends AppCompatActivity {

    //private ActivityResultLauncher<Intent> resultLauncher;

   RecyclerView sns_new_img_rec;
    ImageView sns_new_back, getImage;
    TextView sns_new_share;
    String[] sns_item = {"카메라", "갤러리"};
    Intent intent;
    public static ArrayList<Uri> uriList = new ArrayList<>();
    EditText sns_new_text;
    SnsVO vo = new SnsVO();
    SnsImgVO imgvo = new SnsImgVO();
    GrowthVO gvo = new GrowthVO();

    public final int CAMERA_CODE = 1004;
    public final int GALLERY_CODE = 1005;

    File imgFile = null;
    ArrayList<String> imgFilePathList = new ArrayList<>();
    SnsImgRecAdapter snsImgRecAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_new);
        checkDangerousPermissions();

        sns_new_back = findViewById(R.id.sns_new_back);
        sns_new_share = findViewById(R.id.sns_new_share);
        sns_new_img_rec = findViewById(R.id.sns_new_img_rec);
        sns_new_text = findViewById(R.id.sns_new_text);
        getImage = findViewById(R.id.getImage);


        getImage.setOnClickListener(v -> {
            showDialog();
        });


        sns_new_back.setOnClickListener(v -> {
            finish();
        });

        sns_new_share.setOnClickListener(v -> {
            if(imgFilePathList != null && imgFilePathList.size() > 0) {
              //SnsFragment.img_list.add(imgFilePath);
              //저장 로직
                AskTask addSns = new AskTask("http://121.148.239.238:5524", "share.sn");
                Gson gson = new Gson();

                gvo.setBaby_id(CommonVal.curbaby.getBaby_id());
                gvo.setBaby_gender(CommonVal.curbaby.getBaby_gender());
                gvo.setBaby_name(CommonVal.curbaby.getBaby_name());
                gvo.setGro_content(sns_new_text.getText()+"");

                String testvo = gson.toJson(gvo);
                addSns.addParam("vo",testvo);

               for(int i=0; i<imgFilePathList.size(); i++) {
                   addSns.addFileParam("file"+i, imgFilePathList.get(i));
               }
               CommonMethod.excuteGet(addSns);
               finish();
            }else {
                Toast.makeText(SnsNewActivity.this, "사진을 선택하세요", Toast.LENGTH_SHORT).show();
            }
        });





    }//onCreate

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("업로드 방법 선택")
                .setSingleChoiceItems(sns_item, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        if (sns_item[index].equals("카메라")) {
                            go_Camera();
                        } else {
                            go_gallery();
                        }
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void go_gallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);

    }


    public void go_Camera() {
        //명시적 x , 암시,묵시적 인텐트 실행 => Camera기능을 호출.
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //인텐트가 사용이 가능한지 ( MediaStore기능이 사용가능한지 체크 )
        if (intent.resolveActivity(getPackageManager()) != null) {
            //이미지 파일을 만들고 저장하는 처리가 필요함.
            imgFile = null;
            imgFile = createFile();
            if (imgFile != null) {
                // API 24이상 부터는 FileProvider를 제공해야함
                // Context <=
                Uri imgUri = FileProvider.getUriForFile(
                        getApplicationContext(),
                        getApplicationContext().getPackageName() + ".fileprovider",
                        imgFile
                );
                //버전 분기를 위한 처리.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri); // imgUri를 통해서 카메라로 찍은사진을 받음.
                } else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
                }
                try{

                startActivityForResult(intent, CAMERA_CODE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }//Camera

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
           Toast.makeText(SnsNewActivity.this, "찰칵", Toast.LENGTH_SHORT).show();
           //Glide.with(SnsNewActivity.this).load(imgFilePathList).into(sns_new_img_rec);
        } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
           if(data.getClipData() == null) {
               Toast.makeText(SnsNewActivity.this, "사진을 선택하세요", Toast.LENGTH_SHORT).show();
           }else if(data.getClipData().getItemCount() > 10) {
               Toast.makeText(SnsNewActivity.this, "사진은 열장까지 선택 가능합니다", Toast.LENGTH_SHORT).show();
           }
           else if(data.getClipData() != null) {
               ClipData clipData = data.getClipData();
               Log.e("clipData", String.valueOf(clipData.getItemCount()));
               for(int i =0; i < clipData.getItemCount(); i++) {
                   imgFilePathList.add(getGalleryRealPath(clipData.getItemAt(i).getUri()));

                   snsImgRecAdapter = new SnsImgRecAdapter(imgFilePathList,this);
                   sns_new_img_rec.setAdapter(snsImgRecAdapter);
                  sns_new_img_rec.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
               }
           }
        }
    }

    public File createFile() {
        //파일 이름이 중복되거나 하는것을 방지하기 위한 처리.
        String timeTemp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "My" + timeTemp;
        // 이름만들기 끝 , 경로를 만들기 시작
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);//경로생성을 위한 처리
        File rtnFile = null;

        //임시파일이 생성 됨.

        try {
            rtnFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgFilePathList.add(rtnFile.getAbsolutePath());
        return rtnFile;


    }

    public String getGalleryRealPath(Uri contentUri) {
        String rtnPath = null;
        String[] paths = {MediaStore.Images.Media.DATA};//<=
        Cursor cursor = getContentResolver().query(contentUri, paths, null, null, null);
        if (cursor.moveToFirst()) {
            int columns_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            rtnPath = cursor.getString(columns_index);
        }
        cursor.close();
        return rtnPath;
    }
    // 위험권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}