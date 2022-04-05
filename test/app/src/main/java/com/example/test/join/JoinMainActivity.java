package com.example.test.join;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.my.BabyInfoVO;
import com.example.test.my.FamilyInfoVO;
import com.example.test.my.MyFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

public class JoinMainActivity extends AppCompatActivity {
    Button btn_next;
    ImageView btn_back;
    FrameLayout container;
    TextView tv_id_check;
    static int go = 0;
    static UserVO vo = new UserVO();
    static BabyInfoVO babyInfoVO = new BabyInfoVO();
    static int id_chk = 0;
 //   static String id_chkchk = vo.getId();
    Gson gson = new Gson();
    List<UserVO> list;
    UserFragment userFragment = new UserFragment();
    NewFamilyFragment newFamilyFragment = new NewFamilyFragment(JoinMainActivity.this);
    RelationFragment relationFragment = new RelationFragment();
    BirthFragment birthFragment = new BirthFragment();
    BabyFragment babyFragment = new BabyFragment();
    GenderFragment genderFragment = new GenderFragment();
    PictureFragment pictureFragment = new PictureFragment();
    static int title_result = 0;
    static int result = 0;
    String str;
    public static FamilyInfoVO familyVO = new FamilyInfoVO();


    String family_id ;
    String rels ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_main);

        //아기 추가 시
        Intent intent_my = getIntent();
        str = intent_my.getStringExtra("category");
        if(str != null){
            if(str.equals("new")){
                go = 2;
                changeFrag(new NewFamilyFragment(JoinMainActivity.this));
            }
            else if(str.equals("old")){
                go = 4;
                babyInfoVO.setTitle(familyVO.getTitle());
                changeFrag(new BirthFragment());
            }
        }
        else{
            changeFrag( userFragment);
        }

        //초대코드로 왔을 때
        Intent intent = getIntent();
        family_id = intent.getStringExtra("family_id");
        rels = intent.getStringExtra("rels");
        if(family_id != null){
            changeFrag( new UserFragment(family_id));
        }

        btn_next = findViewById(R.id.btn_next);
        btn_back = findViewById(R.id.btn_back);
        container = findViewById(R.id.constraint);
        //tv_id_check = findViewById(R.id.tv_id_check);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gogo();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

    }//onCreate()


    public void gogo(){//앞으로
        if( go==1 ){
            emptychk();

           if(result == 1){
               allok();
           }

        }else if( go==2 ){
            if( isept( JoinMainActivity.vo.getTitle() , "제목을 입력해주세요.") && title_result==1  )  {
                changeFrag( relationFragment );
                go++;
            }
            if( newFamilyFragment.result==0 ){
                altdialog("입력한 제목을 확인해주세요");
            }
        }else if( go==3 ){
            JoinMainActivity.babyInfoVO.setId(JoinMainActivity.vo.getId());//     babyinfoVO에 id,title담기
            JoinMainActivity.babyInfoVO.setTitle(JoinMainActivity.vo.getTitle());
            changeFrag( birthFragment );
            go++;
        }else if( go==4 ){
            changeFrag( babyFragment );
            go++;
        }else if( go==5 ){
            if( isept( JoinMainActivity.babyInfoVO.getBaby_name() , "아이의 이름을 입력해주세요.")) {
                changeFrag(genderFragment);
                go++;
            }
        }else if( go==6 ){
            go++;
            changeFrag( pictureFragment );
        }else if( go==7 ){
            if(family_id != null){
                emptychk();
                if(result == 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinMainActivity.this);
                    builder.setTitle("회원가입을 완료 하시겠습니까?").setMessage("");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            CommonVal.curuser = JoinMainActivity.vo ;
                            //      회원가입 들어가는 부분
                            if(family_id != null){
                                AskTask invite_task1 = new AskTask(CommonVal.httpip, "user_join.us");
                                Gson gson = new Gson();
                                invite_task1.addParam("vo", gson.toJson(CommonVal.curuser));
                                InputStream invite_in1 = CommonMethod.excuteGet(invite_task1);
                                boolean isSucc1 = gson.fromJson(new InputStreamReader(invite_in1), Boolean.class);
                                if(isSucc1){
                                    AskTask invite_task = new AskTask(CommonVal.httpip, "invite_login.join");
                                    FamilyInfoVO familyInfoVO = new FamilyInfoVO();
                                    familyInfoVO.setTitle(family_id);
                                    familyInfoVO.setFamily_rels(rels);
                                    familyInfoVO.setId(CommonVal.curuser.getId());
                                    invite_task.addParam("vo", gson.toJson(familyInfoVO));
                                    InputStream invite_in = CommonMethod.excuteGet(invite_task);
                                    boolean isSucc2 = gson.fromJson(new InputStreamReader(invite_in), Boolean.class);
                                    if(isSucc2){
                                        CommonVal.curuser = JoinMainActivity.vo ;
                                        //아기 리스트 불러오기
                                        AskTask task = new AskTask(CommonVal.httpip, "list.bif");
                                        //로그인 정보로 수정 필요
                                        task.addParam("id", CommonVal.curuser.getId());
                                        InputStream in = CommonMethod.excuteGet(task);
                                        CommonVal.baby_list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BabyInfoVO>>(){}.getType());
                                        CommonVal.curbaby = CommonVal.baby_list.get(0);

                                        Intent intent = new Intent( JoinMainActivity.this , MainActivity.class );
                                        startActivity(intent);
                                    }
                                }
                            }else{
                                if(user()){
                                    CommonVal.curbaby = JoinMainActivity.babyInfoVO ;
                                    CommonVal.curFamily = JoinMainActivity.babyInfoVO.getTitle() ;
                                    Intent intent = new Intent( JoinMainActivity.this , MainActivity.class );
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        { Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show(); }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(JoinMainActivity.this);
                builder.setTitle("회원가입을 완료 하시겠습니까?").setMessage("");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        CommonVal.curuser = JoinMainActivity.vo ;
                        if(str != null){
                            boolean result = false;
                            babyInfoVO.setId(CommonVal.curuser.getId());
                            if(str.equals("new")){
                                AskTask task = new AskTask(CommonVal.httpip, "insert_new.join");
                                task.addParam("familyvo", gson.toJson(familyVO));
                                task.addParam("babyvo", gson.toJson(babyInfoVO));
                                InputStream in = CommonMethod.excuteGet(task);
                                result = gson.fromJson(new InputStreamReader(in), Boolean.class);
                            } else if(str.equals("old")){
                                AskTask task = new AskTask(CommonVal.httpip, "insert_baby.join");
                                task.addParam("vo", gson.toJson(babyInfoVO));
                                InputStream in = CommonMethod.excuteGet(task);
                                result = gson.fromJson(new InputStreamReader(in), Boolean.class);
                            }
                            if(result){
                                CommonVal.curuser.setId(familyVO.getId());
                                AskTask task_baby = new AskTask(CommonVal.httpip, "list.bif");
                                task_baby.addParam("id", CommonVal.curuser.getId());
                                InputStream in_baby = CommonMethod.excuteGet(task_baby);
                                CommonVal.baby_list = gson.fromJson(new InputStreamReader(in_baby), new TypeToken<List<BabyInfoVO>>(){}.getType());
                                CommonVal.curbaby = CommonVal.baby_list.get(0);
                                Intent intent = new Intent( JoinMainActivity.this , MainActivity.class );
                                startActivity(intent);
                            } else{
                                Toast.makeText(JoinMainActivity.this, "아기 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        //      회원가입 들어가는 부분
                        else if(family_id != null){
                            AskTask invite_task1 = new AskTask(CommonVal.httpip, "user_join.us");
                            Gson gson = new Gson();
                            invite_task1.addParam("vo", gson.toJson(CommonVal.curuser));
                            InputStream invite_in1 = CommonMethod.excuteGet(invite_task1);
                            boolean isSucc1 = gson.fromJson(new InputStreamReader(invite_in1), Boolean.class);
                            if(isSucc1){
                                AskTask invite_task = new AskTask(CommonVal.httpip, "invite_login.join");
                                FamilyInfoVO familyInfoVO = new FamilyInfoVO();
                                familyInfoVO.setTitle(family_id);
                                familyInfoVO.setFamily_rels(rels);
                                familyInfoVO.setId(CommonVal.curuser.getId());
                                invite_task.addParam("vo", gson.toJson(familyInfoVO));
                                InputStream invite_in = CommonMethod.excuteGet(invite_task);
                                boolean isSucc2 = gson.fromJson(new InputStreamReader(invite_in), Boolean.class);
                                if(isSucc2){
                                    CommonVal.curuser = JoinMainActivity.vo ;
                                    //아기 리스트 불러오기
                                    AskTask task = new AskTask(CommonVal.httpip, "list.bif");
                                    //로그인 정보로 수정 필요
                                    task.addParam("id", CommonVal.curuser.getId());
                                    InputStream in = CommonMethod.excuteGet(task);
                                    CommonVal.baby_list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BabyInfoVO>>(){}.getType());
                                    CommonVal.curbaby = CommonVal.baby_list.get(0);

                                    Intent intent = new Intent( JoinMainActivity.this , MainActivity.class );
                                    startActivity(intent);
                                }
                            }
                        }else{
                            if(user()){
                                /*CommonVal.curbaby = JoinMainActivity.babyInfoVO ;
                                CommonVal.curFamily = JoinMainActivity.babyInfoVO.getTitle() ;
                                Intent intent = new Intent( JoinMainActivity.this , MainActivity.class );
                                startActivity(intent);*/
                                CommonVal.curuser = JoinMainActivity.vo ;

                                AskTask task_baby = new AskTask(CommonVal.httpip, "list.bif");
                                task_baby.addParam("id", CommonVal.curuser.getId());
                                InputStream in_baby = CommonMethod.excuteGet(task_baby);
                                CommonVal.baby_list = gson.fromJson(new InputStreamReader(in_baby), new TypeToken<List<BabyInfoVO>>(){}.getType());
                                CommonVal.curbaby = CommonVal.baby_list.get(0);

                                //CommonVal.curbaby = JoinMainActivity.babyInfoVO ;

                                CommonVal.curFamily = JoinMainActivity.babyInfoVO.getTitle() ;
                                Intent intent = new Intent( JoinMainActivity.this , MainActivity.class );
                                startActivity(intent);
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    { Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show(); }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }

    public void back(){//뒤로
        if( go==1 ){
            altDialog();
        }else if( go==2 ){//
            if(str.equals("new")){
                finish();
                MyFragment.my_spinner.setSelection(0);
            } else{
                changeFrag( userFragment );
                go--;
            }

        }else if( go==3 ){
            changeFrag( newFamilyFragment );
            go--;
        }else if( go==4 ){
            if(str.equals("old")){
                finish();
                MyFragment.my_spinner.setSelection(0);
            } else{
                changeFrag( relationFragment );
                go--;
            }
        }else if( go==5 ){
            changeFrag( birthFragment );
            go--;
        }else if( go==6 ){
            changeFrag( babyFragment );
            go--;
        }else if( go==7 ){
            if(family_id != null){
                altDialog();
            }else{
                changeFrag( genderFragment );
                go--;
            }
        }
    }



    @Override
    public void onBackPressed() {
        back();
    }
//    public void backFrag(Fragment fragment){
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
//    }



    public void altDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinMainActivity.this);
        builder.setTitle("회원가입을 종료 하시겠습니까?").setMessage("");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            { finish(); }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            { Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show(); }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void changeFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }



    public void emptychk(){
        String aa = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinMainActivity.this);
        if( userFragment.edt_id.getText().toString().equals( "" ) ){
            String aaa = "";
            builder.setTitle("아이디를 입력해주세요").setMessage("");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if ( !userFragment.id_check() ){
            String aaa = "";
            builder.setTitle("아이디 중복확인을 해주세요").setMessage("");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if ( userFragment.edt_pw.getText().toString().equals("") ){
            String aaa = "";
            builder.setTitle("비밀번호를 입력해주세요").setMessage("");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if ( userFragment.edt_pwchk.getText().toString().equals("") ){
            String aaa = "";
            builder.setTitle("비밀번호를 입력해주세요").setMessage("");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if( !vo.getPw_chk().equals( vo.getPw() )  ) {
            String aaa = "";
            builder.setTitle("비밀번호가 일치하지않습니다").setMessage("");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else {
           /* builder.setTitle("입력을 완료하시겠습니까?").setMessage("");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                  //  backFrag(newFamilyFragment);
                    changeFrag(newFamilyFragment);
                    go++;
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            Log.d("testt", "onClick: " + vo.getId() + "");*/
        }

    }

    public boolean user() {
        AskTask task = new AskTask("http://192.168.0.50", "user.join");
        String uuid = UUID.randomUUID().toString();
        babyInfoVO.setBaby_id(uuid);
        task.addParam("vo", gson.toJson( vo ) );
        task.addParam("vo2", gson.toJson( babyInfoVO ) );
        if( pictureFragment.imgFilePath != null){
            Toast.makeText(JoinMainActivity.this, pictureFragment.imgFilePath, Toast.LENGTH_SHORT).show();
            task.addFileParam("file", pictureFragment.imgFilePath);
        }
        String aa = "";
        InputStream in = CommonMethod.excuteGet(task);
        boolean data = gson.fromJson(new InputStreamReader(in), Boolean.class);
        return data;
        //return false;
    }

    public boolean isept(String checkData , String msg){
        if( checkData == null || checkData.equals("") ){
            altdialog(msg);
            return false;
        }
        return true;
    }


    public void altdialog(String settitle){
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinMainActivity.this);
        builder.setTitle( settitle ).setMessage("");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void allok(){
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinMainActivity.this);
        builder.setTitle("입력을 완료하시겠습니까?").setMessage("");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                changeFrag(newFamilyFragment);
                go++;


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Log.d("testt", "onClick: " + vo.getId() + "");
    }
}