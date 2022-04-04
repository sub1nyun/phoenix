package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.join.JoinMainActivity;
import com.example.test.my.BabyInfoVO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class InviteActivity extends AppCompatActivity {
    Button btn_login, btn_join;
    TextView tv_invite;

    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        tv_invite = findViewById(R.id.tv_invite);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        //app으로 실행 했을 경우 (deeplink 없는 경우)
                        if (pendingDynamicLinkData == null) {
                            //String family_id = deepLink.getQueryParameter("familyId");

                            Log.d("asd: ", "No have dynamic link");
                            //tv.setText(family_id);
                            /*Intent intent = new Intent(InviteLinkActivity.this, SplashActivity.class);
                            startActivity(intent);
                            finish();*/


                        }
                        //deeplink로 app 넘어 왔을 경우
                        //pendingDynamicLinkData != null && deepLink.getBooleanQueryParameter("familyId",false)-- 조건
                        else {
                            deepLink = pendingDynamicLinkData.getLink();
                            String family_id = deepLink.getQueryParameter("familyId");
                            String rels = deepLink.getQueryParameter("rels");
                            AskTask task = new AskTask(CommonVal.httpip, "gettitle.bif");
                            task.addParam("id", family_id);
                            InputStream in = CommonMethod.excuteGet(task);
                            String title = gson.fromJson(new InputStreamReader(in), String.class);
                            Log.d("asd : ", "family_id: " + title);
                            tv_invite.setText(title + "에 " + rels + "로 초대 받으셨습니다.");
                            //tv.setText(family_id);
                            /*deepLink = pendingDynamicLinkData.getLink();
                            Log.d("asd: ", "deepLink: " + deepLink);
                            String segment = deepLink.getLastPathSegment();

                            //uri에 있는 key값 가져오기
                            switch (segment) {
                                case "invite":
                                    String code = deepLink.getQueryParameter("uid");
                                    showCheckDialog(code);      //임의로 dialog로 key값 띄움
                                    break;
                            }*/
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