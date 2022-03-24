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
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, btn_join, btn_forget;
    EditText edt_id, edt_pw;
    CheckBox chk_auto;
    ImageView btn_kakao, btn_naver;
    Button btn_invite, btn_logout;

    Gson gson = new Gson();

    public static OAuthLogin mOAuthLoginModule;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        KakaoSdk.init(this,"884cf31c300f60971b6a3d015d8c005e");

        mContext = getApplicationContext();


        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        btn_forget = findViewById(R.id.btn_forget);
        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        chk_auto = findViewById(R.id.chk_auto);
        btn_kakao = findViewById(R.id.btn_kakao);
       btn_naver = findViewById(R.id.btn_naver);
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
                    AskTask task = new AskTask("http://192.168.0.26", "list.bif");
                    //로그인 정보로 수정 필요
                    task.addParam("id", CommonVal.curuser.getId());
                    InputStream in = CommonMethod.excuteGet(task);
                    CommonVal.baby_list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BabyInfoVO>>(){}.getType());
                    CommonVal.curbaby = CommonVal.baby_list.get(0);

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

        btn_naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginModule = OAuthLogin.getInstance();
                mOAuthLoginModule.init(mContext,
                        getString(R.string.client_id),
                        getString(R.string.client_secret),
                        getString(R.string.client_name));

                OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
                    @Override
                    public void run(boolean success) {
                        if(success) {
                            String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                            String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                            long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                            String tokenType = mOAuthLoginModule.getTokenType(mContext);
                            Log.i("LoginData","accessToken : "+ accessToken);
                            Log.i("LoginData","refreshToken : "+ refreshToken);
                            Log.i("LoginData","expiresAt : "+ expiresAt);
                            Log.i("LoginData","tokenType : "+ tokenType);

                        } else {
                            String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                            String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                            Toast.makeText(mContext, "errorCode:"+errorCode+", errorDesc:"+errorDesc, Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
            }
        });

        btn_logout.setOnClickListener(view -> {
            mOAuthLoginModule.logout(mContext);
            Toast.makeText(LoginActivity.this, "로그아웃 테스트", Toast.LENGTH_SHORT).show();
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







    /////
    /*private fun sendInviteLink(inviteLink: Uri) {
        val teacherName = "최기택" // 임의의 선생님 이름
        val inviteIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain" // 고정 text
            setPackage("com.kakao.talk") // 카카오톡 패키지 지정
            // 초대 코드 텍스트 지정
            putExtra(
                    Intent.EXTRA_TEXT,
                    "$teacherName 선생님이 수업에 초대하였습니다!\n[수업 링크] : $inviteLink"
            )
        }

        try {
            startActivity(inviteIntent) // 수업 초대를 위해 카카오톡 실행
        } catch (e: ActivityNotFoundException) {
            // 카카오톡이 설치되어 있지 않은 경우 예외 발생
            showToast("카카오톡이 설치되어 있지 않습니다.")
        }
    }*/


