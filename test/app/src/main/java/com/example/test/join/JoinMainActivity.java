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
import android.widget.Toast;

import com.example.test.R;

public class JoinMainActivity extends AppCompatActivity {
    Button btn_next;
    ImageView btn_back;
    FrameLayout container;
    static int go = 0;
    static int back = 0;

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
                if( go == 1 ){
                    changeFrag( new BabyFragment() );
                }else if( go == 2 ){
                    changeFrag( new BirthFragment() );
                }else if( go == 3 ){
                    changeFrag( new GenderFragment() );
                }else if( go == 4 ){
                    changeFrag( new RelationFragment() );
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( back == 1 ){
                    /*Intent intent = new Intent( JoinMainActivity.this , LoginActivity.class);
                    startActivity( intent );*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinMainActivity.this);
                    builder.setTitle("회원가입을 종료 하시겠습니까?").setMessage("");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
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

                }else if( back == 2 ){
                    changeFrag( new UserFragment() );
                }else if( back == 3 ){
                    changeFrag( new BabyFragment() );
                }else if( back == 4 ){
                    changeFrag( new BirthFragment() );
                }else if( back == 5 ){
                    changeFrag( new GenderFragment() );
                }
            }
        });



    }//onCreate()






    void changeFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}