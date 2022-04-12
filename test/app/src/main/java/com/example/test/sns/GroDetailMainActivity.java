package com.example.test.sns;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GroDetailMainActivity extends AppCompatActivity {

    ImageView detail_back, baby_icon;
    TextView gro_date, user_comment, de_baby_name;
    RecyclerView grodetailrec;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gro_detail_main);
        binding();

        Intent intent = getIntent();
        String testdata = (String) intent.getSerializableExtra("vo");
        Gson gson = new Gson();
        GrowthVO vo = gson.fromJson(testdata, GrowthVO.class);
        if(vo != null){
            if(vo.getBaby_gender().equals("남아")) {
                baby_icon.setImageResource(R.drawable.tmdwn_boy);
            }else {
                baby_icon.setImageResource(R.drawable.tmdwn_girl);
            }
            list = vo.getImgList();

            de_baby_name.setText(vo.getBaby_name()+"");
            gro_date.setText(vo.getGro_date()+"");
            user_comment.setText(vo.getGro_content()+"");

            GroDeatilAdapter groDeatilAdapter = new GroDeatilAdapter(getLayoutInflater(), list, this);
            grodetailrec.setAdapter(groDeatilAdapter);
            grodetailrec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false
            ));

        }

        detail_back.setOnClickListener(view ->
                finish()
                );


    }

    @SuppressLint("WrongViewCast")
    public void binding() {
        detail_back = findViewById(R.id.detail_back);
        de_baby_name = findViewById(R.id.de_baby_name);
        baby_icon = findViewById(R.id.baby_icon);
        gro_date = findViewById(R.id.gro_date);
        user_comment = findViewById(R.id.user_comment);
        grodetailrec = findViewById(R.id.grodetailrec);
    }
}