package com.example.test.sns;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GroDetailMainActivity extends AppCompatActivity {

    ImageView detail_back, baby_icon;
    TextView gro_date, user_comment, de_baby_name;
    RecyclerView grodetailrec;
    ArrayList<String> list = new ArrayList<>();
    Button detail_btn_del, detail_btn_edit;

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


        detail_btn_del.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("정말 삭제하시겠습니까?").setMessage("");
            builder.setPositiveButton("삭제하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AskTask detaildel = new AskTask(CommonVal.httpip, "delete.sn");
                    detaildel.addParam("no", vo.getGro_no()+"");
                    CommonMethod.excuteGet(detaildel);
                    finish();
                }
            }).show();
            builder.setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        });

        detail_btn_edit.setOnClickListener(v -> {
                AskTask detailedit = new AskTask(CommonVal.httpip, "groselect.sn");
                detailedit.addParam("no", vo.getGro_no()+"");
                InputStream in =CommonMethod.excuteGet(detailedit);

                GrowthVO data = gson.fromJson(new InputStreamReader(in), new TypeToken<GrowthVO>(){}.getType());
                Intent intent1 = new Intent(GroDetailMainActivity.this, EditActivity.class);
                intent1.putExtra("vo", gson.toJson(data));
                startActivity(intent1);
        });



    }

    @SuppressLint("WrongViewCast")
    public void binding() {
        detail_back = findViewById(R.id.detail_back);
        de_baby_name = findViewById(R.id.de_baby_name);
        baby_icon = findViewById(R.id.baby_icon);
        gro_date = findViewById(R.id.gro_date);
        user_comment = findViewById(R.id.user_comment);
        grodetailrec = findViewById(R.id.grodetailrec);
        detail_btn_del =findViewById(R.id.detail_btn_del);
        detail_btn_edit =findViewById(R.id.detail_btn_edit);
    }
}