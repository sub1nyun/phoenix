package com.example.test.sns;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SnsNewActivity extends AppCompatActivity {

    //private ActivityResultLauncher<Intent> resultLauncher;

    //RecyclerView recyclerView;
    ImageView sns_new_back, sns_camera, sns_new_img;
    TextView sns_new_share;
    String[] sns_item = {"카메라", "갤러리"};
    Intent intent;
    public static ArrayList<Uri> uriList = new ArrayList<>();

    public final int CAMERA_CODE = 1004;
    public final int GALLERY_CODE = 1005;

    File imgFile = null;
    String imgFilePath = null;

    Sns_MultiImageAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_new);
        checkDangerousPermissions();

        sns_new_back = findViewById(R.id.sns_new_back);
        sns_new_share = findViewById(R.id.sns_new_share);
        //sns_camera = findViewById(R.id.sns_camera);
        //recyclerView = findViewById(R.id.recyclerView);
        sns_new_img = findViewById(R.id.sns_new_img);


        sns_new_img.setOnClickListener(v -> {
            showDialog();
        });


        sns_new_back.setOnClickListener(v -> {
            finish();
        });


        sns_new_share.setOnClickListener(v -> {
            if(imgFilePath != null) {
                SnsFragment.img_list.add(imgFilePath);
                finish();
            }else {
                Toast.makeText(SnsNewActivity.this, "사진 선택하라고", Toast.LENGTH_SHORT).show();
            }

        });


    }//onCreate

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
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
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true); //다중이미지 허용
//        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);
       // resultLauncher.launch(intent);
        intent.putExtra("paht",imgFilePath);
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
//        if(requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
//            if(data ==null) {
//                Toast.makeText(SnsNewActivity.this, "이미지를 선택하세요", Toast.LENGTH_SHORT).show();
//            }else {
//                if(data.getClipData() ==null){ //이미지하나
//                    Uri imageUri = data.getData();
//                    uriList.add(imageUri);
//
//                    adapter = new Sns_MultiImageAdapter(uriList,this, getLayoutInflater());
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
//                }else { //이미지 여러장
//                    ClipData clipData = data.getClipData();
//                    if(clipData.getItemCount() > 5) {
//                        Toast.makeText(SnsNewActivity.this, "사진은 5장까지 선택 가능합니다", Toast.LENGTH_SHORT).show();
//                    }else {
//                        for(int i=0; i<clipData.getItemCount(); i++) {
//                            Uri imageUri = clipData.getItemAt(i).getUri(); //선택한 이미지들의 uri를 가져옴
//                            try {
//                                uriList.add(imageUri); //uri를 list에 담음
//                            }catch (Exception e) {
//                                Toast.makeText(SnsNewActivity.this, "에러", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        adapter = new Sns_MultiImageAdapter(uriList,this,getLayoutInflater());
//                        recyclerView.setAdapter(adapter);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
//
//                    }
//
//                }
//
//
//
//
//
//            }
       if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
           Toast.makeText(SnsNewActivity.this, "사진을 잘찍었음.", Toast.LENGTH_SHORT).show();
            Glide.with(SnsNewActivity.this).load(imgFilePath).into(sns_new_img);
        } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            Toast.makeText(SnsNewActivity.this, "갤러리 사진 가져옴", Toast.LENGTH_SHORT).show();
            //getContentResolver.query <= 경로를 받아오는 처리. 실제 저장경로 Uri를 알아옴.
            Uri selectImageUri = data.getData();
            imgFilePath = getGalleryRealPath(selectImageUri);
            Glide.with(SnsNewActivity.this).load(imgFilePath).into(sns_new_img);
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
        imgFilePath = rtnFile.getAbsolutePath();
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