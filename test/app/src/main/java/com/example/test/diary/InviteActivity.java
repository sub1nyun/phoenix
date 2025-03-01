package com.example.test.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.LoginActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.join.JoinMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InviteActivity extends AppCompatActivity {
    Button btn_login, btn_join;
    TextView tv_invite1, tv_invite2, tv_invite3;
    ViewPager2 pager;
    CardView baby_img;

    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        tv_invite1 = findViewById(R.id.tv_invite1);
        tv_invite2 = findViewById(R.id.tv_invite2);
        tv_invite3 = findViewById(R.id.tv_invite3);
        pager = findViewById(R.id.pager);
        baby_img = findViewById(R.id.baby_img);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        //app으로 실행 했을 경우 (deeplink 없는 경우)
                        if (pendingDynamicLinkData == null) {
                            Log.d("asd: ", "No have dynamic link");
                        }
                        else {
                            deepLink = pendingDynamicLinkData.getLink();
                            String family_id = deepLink.getQueryParameter("familyId");
                            String rels = deepLink.getQueryParameter("rels");
                            String data = deepLink.getQueryParameter("data");

                            //사진
                            List<String> temp = gson.fromJson(data, new TypeToken<List<String>>(){}.getType());
                            if(temp.size() != 0){
                                baby_img.setVisibility(View.GONE);
                                PagerAdapter adapter = new PagerAdapter(InviteActivity.this, temp);
                                pager.setAdapter(adapter);
                            }



                            AskTask task = new AskTask(CommonVal.httpip, "gettitle.bif");
                            task.addParam("id", family_id);
                            InputStream in = CommonMethod.excuteGet(task);
                            String title = gson.fromJson(new InputStreamReader(in), String.class);
                            Log.d("asd : ", "family_id: " + title);
                            tv_invite1.setText(title);
                            tv_invite2.setText("에");
                            tv_invite3.setText(rels + "로 초대 받으셨습니다.");

                            btn_login.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(InviteActivity.this, LoginActivity.class);
                                    intent.putExtra("family_id",title);
                                    intent.putExtra("rels",rels);
                                    startActivity(intent);
                                }
                            });
                            btn_join.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(InviteActivity.this, JoinMainActivity.class);
                                    intent.putExtra("family_id",title);
                                    intent.putExtra("rels",rels);
                                    startActivity(intent);
                                }
                            });
                        }


                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("asd: ", "getDynamicLink:onFailure", e);
                    }
                });
    }

}