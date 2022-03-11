package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class InviteActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        tv = findViewById(R.id.tv);

        handleDeepLink();
    }

    private void handleDeepLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        //app으로 실행 했을 경우 (deeplink 없는 경우)
                        if (pendingDynamicLinkData == null) {
                            String family_id = deepLink.getQueryParameter("uid");
                            Log.d("asd: ", "No have dynamic link");
                            tv.setText(family_id);
                            /*Intent intent = new Intent(InviteLinkActivity.this, SplashActivity.class);
                            startActivity(intent);
                            finish();*/
                        }
                        //deeplink로 app 넘어 왔을 경우
                        if(deepLink != null && deepLink.getBooleanQueryParameter("uid",false)){
                            String family_id = deepLink.getQueryParameter("uid");
                            Log.d("asd : ", "family_id: " + family_id);
                            tv.setText(family_id);
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