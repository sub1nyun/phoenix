package com.example.test.join;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class PictureFragment extends Fragment {

    RoundedImageView imv_photo;
    //ImageView imv_camera;


    String[] span_item = {"카메라", "갤러리"};
    public final int CAMERA_CODE = 1004;
    public final int GELLARY_CODE = 1005;
    File imgFile = null;
    String imgFilePath = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_picture, container, false);
        checkDangerousPermissions();
        imv_photo = rootview.findViewById(R.id.imv_photo);
        //imv_camera = rootview.findViewById(R.id.imv_camera);
        imv_photo.setBackground(Drawable.createFromPath("@drawable/bss_logo"));


        if(JoinMainActivity.babyInfoVO.getBaby_photo() != null){
            //imv_photo.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(imgFilePath).into(imv_photo);
            //JoinMainActivity.vo.setKakao_id("sdf");      값 test
            //Toast.makeText(getContext(), JoinMainActivity.babyInfoVO.getBaby_photo()+"", Toast.LENGTH_SHORT).show();
        }/*else{
            imv_photo.setVisibility(View.GONE);
        }*/



        imv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return rootview;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("업로드 방법 선택")
                .setSingleChoiceItems(span_item, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        if(span_item[index].equals("카메라")){
                            go_Camera();
                        }else{
                            go_gallery();
                        }
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void go_gallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser( intent , "Select Picture"   ) , GELLARY_CODE );
    }

    public void go_Camera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getContext().getPackageManager() ) != null){
            imgFile = null ;
            imgFile = createFile();

            if(imgFile != null){
                Uri imgUri = FileProvider.getUriForFile(
                        ((JoinMainActivity)getActivity()).getApplicationContext(),
                        ((JoinMainActivity)getActivity()).getApplicationContext().getPackageName()+".fileprovider",
                        imgFile
                );
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    intent.putExtra(MediaStore.EXTRA_OUTPUT , imgUri);
                }else{
                    intent.putExtra(MediaStore.EXTRA_OUTPUT , Uri.fromFile(imgFile));
                }
                startActivityForResult(intent , CAMERA_CODE);
            }
        }
    }

    public String getGalleryRealPath(Uri contentUri){
        String rtnPath = null;
        String[] paths = {MediaStore.Images.Media.DATA};//<=
        Cursor cursor = ((JoinMainActivity)getActivity()).getContentResolver().query(contentUri , paths , null , null , null);
        if(cursor.moveToFirst()){
            int columns_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            rtnPath = cursor.getString(columns_index);
        }
        cursor.close();
        JoinMainActivity.babyInfoVO.setBaby_photo( rtnPath );

        return rtnPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_CODE && resultCode == getActivity().RESULT_OK){
            imv_photo.setBackground(null);
            Glide.with(getContext()).load(imgFilePath).into(imv_photo);

        }else if(requestCode == GELLARY_CODE && resultCode == getActivity().RESULT_OK){
            imv_photo.setBackground(null);
            Uri selectImageUri = data.getData();
            imgFilePath = getGalleryRealPath(selectImageUri);
            Glide.with(getContext()).load(imgFilePath).into(imv_photo);
        }
    }

    //파일 생성을 위한처리 .
    public File createFile(){
        String timeTemp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "My" + timeTemp ;
        File storageDir = ((JoinMainActivity)getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File rtnFile = null ;
        try {
            rtnFile = File.createTempFile(imageFileName , ".jpg" , storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgFilePath = rtnFile.getAbsolutePath();
        JoinMainActivity.babyInfoVO.setBaby_photo( imgFilePath );
        return rtnFile;
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(getContext(), permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
                Toast.makeText(getContext(), "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
