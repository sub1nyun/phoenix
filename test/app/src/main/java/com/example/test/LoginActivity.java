package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.join.JoinMainActivity;
import com.example.test.my.BabyInfoVO;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.oauth.view.NidOAuthLoginButton;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfileResponse;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, btn_join, btn_forget;
    EditText edt_id, edt_pw;
    CheckBox chk_auto;
    ImageView btn_kakao;
    Button btn_invite, btn_logout;


    NidOAuthLoginButton naverlogin;
    Gson gson = new Gson();
    public static OAuthLogin mOAuthLoginModule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        KakaoSdk.init(this,"884cf31c300f60971b6a3d015d8c005e");
        NaverIdLoginSDK.INSTANCE.initialize(LoginActivity.this,"uR4I8FNC11hwqTB3Fr6l","U3LRpxH6Tq","BSS");





        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        btn_forget = findViewById(R.id.btn_forget);
        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        chk_auto = findViewById(R.id.chk_auto);
        btn_kakao = findViewById(R.id.btn_kakao);
        naverlogin = findViewById(R.id.btn_naver);
        btn_logout = findViewById(R.id.btn_logout);



        ////초대 버튼 임시 생성
        btn_invite = findViewById(R.id.btn_invite);
        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDynamicLink();
            }
        });

        Function2<OAuthToken, Throwable, Unit> callBack = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

                if(throwable != null) {
                    Toast.makeText(LoginActivity.this, "오류"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }if(oAuthToken != null){
                    Toast.makeText(LoginActivity.this, "받아옴", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        };


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_id.getText().toString().equals("a") && edt_pw.getText().toString().equals("a")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    //로그인 정보 저장
                    CommonVal.curuser.setId("a");
                    CommonVal.curuser.setPw("a");

                    //아기 리스트 불러오기
                    AskTask task = new AskTask(CommonVal.httpip, "list.bif");
                    //로그인 정보로 수정 필요
                    task.addParam("id", CommonVal.curuser.getId());
                    InputStream in = CommonMethod.excuteGet(task);
                    CommonVal.baby_list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BabyInfoVO>>(){}.getType());
                    CommonVal.curbaby = CommonVal.baby_list.get(1);

                   // 가족정보 불러오기
                    task = new AskTask(CommonVal.httpip, "titlelist.us");
                    task.addParam("id", CommonVal.curuser.getId());
                    in = CommonMethod.excuteGet(task);
                    CommonVal.family_title = gson.fromJson(new InputStreamReader(in), new TypeToken<List<String>>(){}.getType());
                    CommonVal.curFamily =  CommonVal.family_title.get(0);

                   finish();
                }
            }
        });
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callBack);
                }else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callBack);
                }
            }
        });



        naverLogin();


        btn_logout.setOnClickListener(v -> {
            NaverIdLoginSDK.INSTANCE.logout();
            Toast.makeText(LoginActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
        });
    }

    public void naverLogin(){
        NidOAuthLogin authLogin = new NidOAuthLogin();
        naverlogin.setOAuthLoginCallback(new OAuthLoginCallback() {
            @Override
            public void onSuccess() {
                Log.d("naver" ,"onSuccess:성공");
                Log.d("naver", NaverIdLoginSDK.INSTANCE.getAccessToken());
                authLogin.callProfileApi(new NidProfileCallback<NidProfileResponse>() {
                    @Override
                    public void onSuccess(NidProfileResponse nidProfileResponse) {
                        Log.d("naver","onSuccess:성공" + nidProfileResponse.getProfile().getEmail());
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("email", nidProfileResponse.getProfile().getEmail());
                        intent.putExtra("name", nidProfileResponse.getProfile().getName());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, @NonNull String s) {
                        Log.d("naver", "OnSuccess:실패" +s);
                        Log.d("naver", NaverIdLoginSDK.INSTANCE.getLastErrorCode().getCode());
                        Log.d("naver", NaverIdLoginSDK.INSTANCE.getLastErrorDescription());
                    }

                    @Override
                    public void onError(int i, @NonNull String s) {
                        Log.d("naver","OnSuccess:오류" + s);

                    }
                });
            }

            @Override
            public void onFailure(int i, @NonNull String s) {
                Log.d("naver", "OnSuccess:실패" +s);
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.d("naver", "OnSuccess:에러" +s);
            }
        });

    }

    public void changeFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }


    private void createDynamicLink() {
        String familyId = "tmdwn12345";
        String invitationLink = "https://babysmilesupport.page.link/invite?familyId="+familyId; //생성할 다이나믹 링크

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(invitationLink))    //정보를 담는 json 사이트를 넣자!!
                .setDomainUriPrefix("https://babysmilesupport.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();   //긴 URI
        Log.d("asd: ", "long uri : " + dynamicLinkUri);

    }



}//Class



