package com.example.test.my;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.AddFragment;
import com.example.test.MainActivity;
import com.example.test.OnBackPressedListenser;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class EditFragment extends Fragment implements OnBackPressedListenser {
    Button my_rels, btn_man, btn_woman, btn_save, btn_del;
    LinearLayout edit_birth;
    TextView tv_birth, edit_ok;
    ImageView edit_cancel, imv_camera, img_boy, img_girl;
    RoundedImageView edit_photo;
    EditText edit_name;
    //Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("JST"));
    private  DatePickerDialog.OnDateSetListener callbackMethod;
    private TimePickerDialog.OnTimeSetListener callbackTime;
    int y=0, m=0, d=0, h=0, mi=0;
    String[] span_item = {"카메라", "갤러리", "기본사진"};
    public final int CAMERA_CODE = 1004;
    public final int GELLARY_CODE = 1005;
    File imgFile = null;
    String imgFilePath = null;
    Gson gson = new Gson();
    BabyInfoVO vo;
    FamilyInfoVO family;
    String tempImg = null;

    public EditFragment(BabyInfoVO vo) {
        this.vo = vo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_edit, container, false);
        checkDangerousPermissions();

        my_rels = rootView.findViewById(R.id.my_rels);
        edit_birth = rootView.findViewById(R.id.edit_birth);
        tv_birth = rootView.findViewById(R.id.tv_birth);
        edit_cancel = rootView.findViewById(R.id.edit_cancel);
        edit_name = rootView.findViewById(R.id.edit_name);
        btn_man = rootView.findViewById(R.id.btn_man);
        btn_woman = rootView.findViewById(R.id.btn_woman);
        edit_photo = rootView.findViewById(R.id.edit_photo);
        btn_save = rootView.findViewById(R.id.btn_save);
        btn_del = rootView.findViewById(R.id.btn_del);
        edit_ok = rootView.findViewById(R.id.edit_ok);
        imv_camera = rootView.findViewById(R.id.imv_camera);
        img_boy = rootView.findViewById(R.id.img_boy);
        img_girl = rootView.findViewById(R.id.img_girl);

        //초기 세팅
        if(vo.getBaby_photo() == null){
            if(vo.getBaby_gender().equals("여아")){
                edit_photo.setVisibility(View.VISIBLE);
                img_boy.setVisibility(View.GONE);
                img_girl.setVisibility(View.VISIBLE);
            } else{
                edit_photo.setVisibility(View.GONE);
                img_girl.setVisibility(View.GONE);
                img_boy.setVisibility(View.VISIBLE);
            }
        } else{
            edit_photo.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(vo.getBaby_photo()).into(edit_photo);
            tempImg = vo.getBaby_photo();
        }
        edit_name.setText(vo.getBaby_name());
        changeBtn(vo.getBaby_gender());
        tv_birth.setText(vo.getBaby_birth().toString());

        //아이와의 관계 불러오기
        AskTask task = new AskTask(CommonVal.httpip, "rels.bif");
        task.addParam("id", CommonVal.curuser.getId()); //로그인한 아이디로 변경 필요
        task.addParam("baby_id", vo.getBaby_id());
        InputStream in = CommonMethod.excuteGet(task);
        family = gson.fromJson(new InputStreamReader(in), new TypeToken<FamilyInfoVO>(){}.getType());
        my_rels.setText(family.getFamily_rels());

        //뒤로가기
        edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_cancel = new AlertDialog.Builder(getContext()).setTitle("취소").setMessage("현재 수정하신 내용이 저장되지 않습니다.\n정말 취소하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().getSupportFragmentManager().beginTransaction().remove(EditFragment.this).commit();
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_cancel.create();
                alertDialog.show();
            }
        });

        //저장
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dto 수정
                vo.setBaby_name(edit_name.getText().toString().trim());
                vo.setBaby_birth(tv_birth.getText().toString());
                family.setFamily_rels(my_rels.getText().toString().trim());
                AskTask task_save = new AskTask(CommonVal.httpip, "updatebaby.bif");
                task_save.addParam("vo", gson.toJson(vo));
                if(imgFilePath != null){
                    Toast.makeText(getContext(), imgFilePath, Toast.LENGTH_SHORT).show();
                    task_save.addFileParam("file", imgFilePath);
                }
                task_save.addParam("family", gson.toJson(family));
                InputStream in = CommonMethod.excuteGet(task_save);

                //아기 리스트 불러오기
                AskTask task = new AskTask(CommonVal.httpip, "list.bif");
                //로그인 정보로 수정 필요
                task.addParam("id", CommonVal.curuser.getId());
                InputStream in_re = CommonMethod.excuteGet(task);
                CommonVal.baby_list = gson.fromJson(new InputStreamReader(in_re), new TypeToken<List<BabyInfoVO>>(){}.getType());

                ((MainActivity)getActivity()).changeFrag(new MyFragment());
                tempImg = null;
            }
        });
        edit_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_save.callOnClick();
            }
        });

        //아이 정보 삭제
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_delete = new AlertDialog.Builder(getContext()).setTitle("아이 정보 삭제").setMessage("현재까지 기록한 아이 기록이 모두 사라집니다.\n정말 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AskTask task_delete = new AskTask(CommonVal.httpip, "babydel.bif");
                                task_delete.addParam("baby_id", vo.getBaby_id());
                                task_delete.addParam("title", vo.getTitle());
                                InputStream in = CommonMethod.excuteGet(task_delete);
                                if(gson.fromJson(new InputStreamReader(in), Boolean.class)) { //아기 삭제 성공
                                    Toast.makeText(getContext(), "아기 정보가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    //아기 목록 다시 불러오기
                                    if(CommonVal.baby_list.size() == 1){ //아기 1명을 삭제해서 더이상 아기 없음
                                        AskTask task = new AskTask(CommonVal.httpip, "deltitle.bif");
                                        task.addParam("title", vo.getTitle());
                                        task.addParam("id", vo.getId());
                                        InputStream del_in = CommonMethod.excuteGet(task); //육아일기 삭제

                                        CommonVal.baby_list.clear();
                                        CommonVal.curbaby = null;
                                        //add 프래그먼트 띄우기
                                        ((MainActivity)getActivity()).changeFrag(new AddFragment());
                                    } else{ //아기 더 있으므로 다시 리스트 불러옴
                                       CommonVal.baby_list.remove(vo);

                                        int count = 0;
                                        for (int i = 0; i < CommonVal.baby_list.size(); i++) { //현재 삭제한 아기가 있던 육아일기에 아기가 더 있는지 확인
                                            if (CommonVal.baby_list.get(i).getTitle().equals(vo.getTitle())) { //육아일기에 아기가 더 있음
                                                count += 1;
                                            }
                                        }

                                        if (count == 0) {
                                            AskTask task = new AskTask(CommonVal.httpip, "deltitle.bif");
                                            task.addParam("title", vo.getTitle());
                                            task.addParam("id", vo.getId());
                                            InputStream del_in = CommonMethod.excuteGet(task);
                                            CommonVal.family_title.remove(vo.getTitle());
                                        }
                                        CommonVal.curbaby = CommonVal.baby_list.get(0);

                                        ((MainActivity) getActivity()).changeFrag(new MyFragment());
                                    }
                                } else{ //아기 삭제 실패
                                    Toast.makeText(getContext(), "아기 정보 삭제를 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_delete.create();
                alertDialog.show();
            }
        });

        //관계
        my_rels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelsDialog dialog = new RelsDialog(getContext(), family.getFamily_rels());
                //현재 관계 넘기기
                dialog.show();
                dialog.setDialogListener(new RelsDialog.DialogListener() {
                    @Override
                    public void onPositiveClick(String name) {
                        my_rels.setText(name);
                    }
                });
            }
        });

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y = year;
                m = monthOfYear+1;
                d = dayOfMonth;

                String time = vo.getBaby_birth().split(" ")[1];
                String times[] = new String[2];
                times = time.split(":");
                TimePickerDialog dialog = new TimePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackTime, Integer.parseInt(times[0]), Integer.parseInt(times[1]), false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        };

        callbackTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;
                vo.setBaby_birth(y + "-" + (m/10<1 ? "0" + m : m)  + "-" + (d/10<1 ? "0" + d : d) + " " + (h/10<1 ? "0" + h : h) + ":" + (mi/10<1 ? "0" + mi : mi));
                tv_birth.setText(y + "-" + (m/10<1 ? "0" + m : m)  + "-" + (d/10<1 ? "0" + d : d) + " " + (h/10<1 ? "0" + h : h) + ":" + (mi/10<1 ? "0" + mi : mi));
            }
        };

        //출생일
        edit_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = vo.getBaby_birth().split(" ")[0];
                String dates[] = new String[3];
                dates = date.split("-");

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), callbackMethod, Integer.parseInt(dates[0]), Integer.parseInt(dates[1])-1, Integer.parseInt(dates[2]));
                datePickerDialog.show();
            }
        });

        //사진
        imv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //성별
        btn_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vo.setBaby_gender("남아");
                changeBtn("남아");
            }
        });

        btn_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vo.setBaby_gender("여아");
                changeBtn("여아");
            }
        });
        return rootView;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("업로드 방법 선택")
                .setSingleChoiceItems(span_item, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        if(span_item[index].equals("카메라")){
                            go_Camera();
                        }else if(span_item[index].equals("갤러리")){
                            go_gallery();
                        } else{
                            edit_photo.setVisibility(View.GONE);
                            if(vo.getBaby_gender().equals("여아")){
                                img_boy.setVisibility(View.GONE);
                                img_girl.setVisibility(View.VISIBLE);
                            } else{
                                img_boy.setVisibility(View.VISIBLE);
                                img_girl.setVisibility(View.GONE);
                            }
                            //이미지 파일 널처리
                            imgFile = null;
                            imgFilePath = null;
                            tempImg = null;
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
                        ((MainActivity)getActivity()).getApplicationContext(),
                        ((MainActivity)getActivity()).getApplicationContext().getPackageName()+".fileprovider",
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
        Cursor cursor = ((MainActivity)getActivity()).getContentResolver().query(contentUri , paths , null , null , null);
        if(cursor.moveToFirst()){
            int columns_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            rtnPath = cursor.getString(columns_index);
        }
        cursor.close();
        return rtnPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_CODE && resultCode == getActivity().RESULT_OK){
            edit_photo.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(imgFilePath).into(edit_photo);
        }else if(requestCode == GELLARY_CODE && resultCode == getActivity().RESULT_OK){
            edit_photo.setVisibility(View.VISIBLE);
            Uri selectImageUri = data.getData();
            imgFilePath = getGalleryRealPath(selectImageUri);
            Glide.with(getContext()).load(imgFilePath).into(edit_photo);
            tempImg = imgFilePath;
        }
    }

    //파일 생성을 위한처리 .
    public File createFile(){
        String timeTemp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "My" + timeTemp ;
        File storageDir = ((MainActivity)getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File rtnFile = null ;
        try {
            rtnFile = File.createTempFile(imageFileName , ".jpg" , storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgFilePath = rtnFile.getAbsolutePath();
        tempImg = rtnFile.getAbsolutePath();
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


    //플래그먼트 백버튼 처리
    @Override
    public void onBackPressed() {
        edit_cancel.callOnClick();
    }

    //버튼 색 변경
    public void changeBtn(String gender){
        if(gender.equals("남아")){
            btn_man.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_round_gray_fill));
            btn_woman.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_round_gray));
            if(tempImg == null) {
                edit_photo.setVisibility(View.GONE);
                img_boy.setVisibility(View.VISIBLE);
                img_girl.setVisibility(View.GONE);
            }
        } else if(gender.equals("여아")){
            btn_woman.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_round_gray_fill));
            btn_man.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_round_gray));
            if(tempImg == null){
                edit_photo.setVisibility(View.GONE);
                img_boy.setVisibility(View.GONE);
                img_girl.setVisibility(View.VISIBLE);
            }
        }
    }
}