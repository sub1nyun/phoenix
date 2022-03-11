package com.example.test.join;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.test.R;

public class JoinMainActivity extends AppCompatActivity {
    Button btn_next;
    ImageView btn_back;
    FrameLayout container;
    static int go = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_main);
        changeFrag( new UserFragment() );

        btn_next = findViewById(R.id.btn_next);
        btn_back = findViewById(R.id.btn_back);
        container = findViewById(R.id.constraint);



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( go == 0 ){
                    changeFrag( new UserFragment() );
                }else if( go == 1 ){
                    changeFrag( new NewFamilyFragment() );
                }else if( go == 2 ){
                    changeFrag( new ChildBirthFragment() );
                }else if( go == 3 ){
                    changeFrag( new BirthFragment() );
                }else if( go == 4 ){
                    changeFrag( new BabyFragment() );
                }else if( go == 5 ){
                    changeFrag( new GenderFragment() );
                }else if( go == 6 ){
                    changeFrag( new RelationFragment() );
                }else if( go == 7 ){
                    changeFrag( new PictureFragment() );
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( go == 1 ){
                    /*Intent intent = new Intent( JoinMainActivity.this , LoginActivity.class);
                    startActivity( intent );*/
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

                }else if( go==2 ){
                    changeFrag( new UserFragment() );
                }else if( go==3 ){
                    changeFrag( new NewFamilyFragment() );
                }else if( go==4 ){
                    changeFrag( new ChildBirthFragment() );
                }else if( go==5 ){
                    changeFrag( new BirthFragment() );
                }else if( go==6 ){
                    changeFrag( new BabyFragment() );
                }else if( go==7 ){
                    changeFrag( new GenderFragment() );
                }else if( go==8 ){
                    changeFrag( new RelationFragment() );
                }
            }
        });



    }//onCreate()






    void changeFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}