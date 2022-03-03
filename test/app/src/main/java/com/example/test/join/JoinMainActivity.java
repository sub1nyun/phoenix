package com.example.test.join;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.test.R;

public class JoinMainActivity extends AppCompatActivity {
    Button btn_next;
    ImageView btn_back;
    FrameLayout container;
    static int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_main);

        btn_next = findViewById(R.id.btn_next);
        btn_back = findViewById(R.id.btn_back);
        container = findViewById(R.id.constraint);


        changeFrag(new UserFragment() );


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( a == 0 ){
                    changeFrag( new BabyFragment() );
                }
            }
        });



    }




    public void changeFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}