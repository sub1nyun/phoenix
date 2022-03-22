package com.example.test.join;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test.MainActivity;
import com.example.test.R;

public class JoinMainActivity extends AppCompatActivity {
    Button btn_next;
    ImageView btn_back;
    FrameLayout container;
    static int go = 0;
    static UserVO vo = new UserVO();
    static int id_chk = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_main);
        changeFrag( new UserFragment() );

        //초대코드로 왔을 때
        Intent intent = getIntent();
        String family_id = intent.getStringExtra("family_id");
        if(family_id != null){
            changeFrag( new UserFragment(family_id) );
        }

        btn_next = findViewById(R.id.btn_next);
        btn_back = findViewById(R.id.btn_back);
        container = findViewById(R.id.constraint);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( go == 0 ){
                    changeFrag( new UserFragment() );
                }else if( go == 1 ){
                    id_chk = 1;
                    emptychk();
                    changeFrag( new NewFamilyFragment() );//제목

                }else if( go == 2 ){
                    changeFrag( new RelationFragment() );//관계
                }else if( go == 3 ){
                    changeFrag( new BirthFragment() );//출생일
                }else if( go == 4 ){
                    changeFrag( new BabyFragment() );//이름
                }else if( go == 5 ){
                    changeFrag( new GenderFragment() );//성별
                }else if( go == 6 ){
                    changeFrag( new PictureFragment() );//사진
                }else if( go == 7 ){
                    //changeFrag( new ChildBirthFragment() );
                    Intent intent = new Intent(JoinMainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( go == 1 ){
                    /*Intent intent = new Intent( JoinMainActivity.this , LoginActivity.class);
                    startActivity( intent );*/
                    altDialog();
                }else if( go==2 ){
                    changeFrag( new UserFragment() );
                }else if( go==3 ){
                    changeFrag( new NewFamilyFragment() );
                }else if( go==4 ){
                    changeFrag( new RelationFragment() );
                }else if( go==5 ){
                    changeFrag( new BirthFragment() );
                }/*else if( go==6 ){
                    changeFrag( new BabyFragment() );
                }*/else if( go==6 ){
                    changeFrag( new GenderFragment() );
                }else if( go==7 ){
                    if(family_id != null){
                        altDialog();
                    }else{
                        changeFrag( new PictureFragment() );
                    }
                }
            }
        });
    }//onCreate()

    public void altDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinMainActivity.this);
        builder.setTitle("회원가입을 종료 하시겠습니까?").setMessage("");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    void changeFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    public int id_check( int id_chk){
        String aa = "";
        /*AskTask task = new AskTask("id_check");
        task.addParam("","");*/
        return id_chk;
    }

    public void emptychk(){
        String aa = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinMainActivity.this);
        if( vo.getId() == null ){
            String aaa = "";
            builder.setTitle("아이디를 입력해주세요").setMessage("");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if ( id_chk == 0 ){
            String aaa = "";
            builder.setTitle("아이디 중복확인을 해주세요").setMessage("");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if ( vo.getPw() == null ){
            String aaa = "";
            builder.setTitle("비밀번호를 입력해주세요").setMessage("");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if ( vo.getPw_chk() == null ){
            String aaa = "";
            builder.setTitle("비밀번호확인을 입력해주세요").setMessage("");
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
            String aaa = "";
            changeFrag( new NewFamilyFragment() );//제목

        }

    }


}