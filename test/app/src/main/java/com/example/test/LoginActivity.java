package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.join.JoinMainActivity;
import com.example.test.join.UserVO;
import com.example.test.my.BabyInfoVO;
import com.example.test.my.FamilyInfoVO;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
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
    private static final String TAG = "테스트" ;
    Button btn_login, btn_forget;
    EditText edt_id, edt_pw;
    CheckBox chk_auto;
    ImageView btn_kakao, imv_naver;
    Button btn_logout;
    String id , pw;

    String invite_title = null;
    String invite_rels = null;

    NidOAuthLoginButton naverlogin;
    Gson gson = new Gson();
    public static OAuthLogin mOAuthLoginModule;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: aa");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //key
        KakaoSdk.init(this,"9bb5096013cc3ff738a2ca42f3fd61d1");
        NaverIdLoginSDK.INSTANCE.initialize(LoginActivity.this,"uR4I8FNC11hwqTB3Fr6l","U3LRpxH6Tq","BSS");

        binding();

//        ////초대 버튼 임시 생성
//        btn_invite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createDynamicLink();
//            }
//        });

        //자동로그인
        SharedPreferences preferences = getPreferences(LoginActivity.MODE_PRIVATE);
        id = preferences.getString("id","");
        pw = preferences.getString("pw","");
        Boolean isLogin = preferences.getBoolean("autologin" , false);
        chk_auto.setChecked(isLogin); // 자동로그인을 체크하고나서 앱을 종료해도 그대로 저장된상태를 보여줌.

        Function2<OAuthToken, Throwable, Unit> callBack = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

                if(throwable != null) {
                    Toast.makeText(LoginActivity.this, "오류"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }if(oAuthToken != null){
                    Toast.makeText(LoginActivity.this, "받아옴", Toast.LENGTH_SHORT).show();
                    getKakaoInfo();
                }
                return null;
            }
        };

        Intent invite_intent = getIntent();
        invite_title = invite_intent.getStringExtra("family_id");
        invite_rels = invite_intent.getStringExtra("rels");
        //Log.d("", "invite: "+invite_title);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (edt_id.getText().toString().equals("a") && edt_pw.getText().toString().equals("a")) {
                if( vaildchk() ) {
                    boolean login = login();

                    if ( login ) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        //로그인 정보 저장
                  /*  CommonVal.curuser.setId( edt_id.getText().toString() );
                    CommonVal.curuser.setPw( edt_id.getText().toString() );
*/
                        //초대로 왔을 때
                        if (invite_title != null) {
                            if(!in_family(invite_title)) {
                                AskTask invite_task = new AskTask(CommonVal.httpip, "invite_login.join");
                                FamilyInfoVO familyInfoVO = new FamilyInfoVO();
                                familyInfoVO.setTitle(invite_title);
                                familyInfoVO.setFamily_rels(invite_rels);
                                familyInfoVO.setId(CommonVal.curuser.getId());
                                Gson gson = new Gson();
                                invite_task.addParam("vo", gson.toJson(familyInfoVO));
                                InputStream invite_in = CommonMethod.excuteGet(invite_task);
                                boolean isSucc = gson.fromJson(new InputStreamReader(invite_in), Boolean.class);
                            }
                        }


                        //아기 리스트 불러오기
                        AskTask task = new AskTask(CommonVal.httpip, "list.bif");
                        //로그인 정보로 수정 필요
                        task.addParam("id", CommonVal.curuser.getId());
                        InputStream in = CommonMethod.excuteGet(task);
                        CommonVal.baby_list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BabyInfoVO>>() {}.getType());
                        if (CommonVal.baby_list.size() != 0) {
                            CommonVal.curbaby = CommonVal.baby_list.get(1);
                        }

                        // 가족정보 불러오기
                        task = new AskTask(CommonVal.httpip, "titlelist.us");
                        task.addParam("id", CommonVal.curuser.getId());
                        in = CommonMethod.excuteGet(task);
                        CommonVal.family_title = gson.fromJson(new InputStreamReader(in), new TypeToken<List<String>>() {
                        }.getType());

                        finish();
                    }
                }

            }
        });
        /*btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinMainActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
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
        imv_naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("네이버", "onClick: ");
                naverLogin();
                naverlogin.callOnClick();
            }
        });



        if(isLogin){
            edt_id.setText(id);
            edt_pw.setText(pw);
            btn_login.callOnClick(); // OnClick을 강제로 실행함.
        }

    }//onCreate

    public boolean in_family(String invite_title){
        AskTask task = new AskTask(CommonVal.httpip, "family_selectid.join");
        FamilyInfoVO familyInfoVO = new FamilyInfoVO();
        familyInfoVO.setTitle(invite_title);
        familyInfoVO.setId(CommonVal.curuser.getId());
        Gson gson = new Gson();
        task.addParam("vo", gson.toJson(familyInfoVO));
        InputStream in = CommonMethod.excuteGet(task);
        boolean isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);
        return isSucc;
    }

    private void binding() {
        btn_login = findViewById(R.id.btn_login);
        //btn_join = findViewById(R.id.btn_join);
        btn_forget = findViewById(R.id.btn_forget);
        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        chk_auto = findViewById(R.id.chk_auto);
        btn_kakao = findViewById(R.id.btn_kakao);
        imv_naver = findViewById(R.id.imv_naver);
        naverlogin = findViewById(R.id.btn_naver);
        //btn_logout = findViewById(R.id.btn_logout);
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
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        intent.putExtra("email", nidProfileResponse.getProfile().getEmail());
//                        intent.putExtra("name", nidProfileResponse.getProfile().getName());
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        startActivity(intent);
//                        finish();
      //                  Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        String email = nidProfileResponse.getProfile().getEmail();
                        //getinfo( email );
                        AskTask task = new AskTask( CommonVal.httpip,"social_login.user");
                        task.addParam("id" , email);
                        InputStream in =  CommonMethod.excuteGet(task);
                        Gson gson = new Gson();
                        boolean data = gson.fromJson(new InputStreamReader(in) , Boolean.class);
                        //String aa = "";
                        if( data ){
                            getinfo(email);
                        }else{
                            //아이디가 없음
                            altdialog("알림" ,"아이디가 없어 회원가입 화면으로 이동합니다.");
                            Intent intent = new Intent(LoginActivity.this , JoinMainActivity.class);
                            startActivity(intent);

                        }

       //                 intent.putExtra("email", nidProfileResponse.getProfile().getEmail());
       //                 intent.putExtra("name", nidProfileResponse.getProfile().getName());
      //                  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      //                  intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
      //                  startActivity(intent);

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


    public void saveLoginInfo() {
        //체크박스 자동로그인이 체크가 된 상태라면 임시 데이터를 저장함 ( 로그인 정보를 )
        try {
            preferences  = getPreferences(LoginActivity.MODE_PRIVATE);
            editor = preferences.edit();
            if (chk_auto.isChecked()) { //로그인 정보를 저장함.
                editor.putBoolean("autologin" , true);
                editor.putString("id", edt_id.getText() + "");
                editor.putString("pw", edt_pw.getText() + "");
                editor.putBoolean("first" , true);
            } else {  // 로그인 정보를 삭제함.
                editor.remove("autologin");
                editor.remove("id");
                editor.remove("pw");
                //editor.clear();
            }
            editor.apply();
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "자동로그인 정보 저장 실패.", Toast.LENGTH_SHORT).show();
        }
    }
    //login idpw chk
    public boolean login () {
        //id,pw유무
        AskTask login_task = new AskTask(CommonVal.httpip, "bssLoginn");

        login_task.addParam("id", edt_id.getText().toString() );
        login_task.addParam("pw", edt_pw.getText().toString() );
        InputStream login_in = CommonMethod.excuteGet(login_task);
        CommonVal.curuser = gson.fromJson(new InputStreamReader(login_in), UserVO.class);
        if (CommonVal.curuser != null ){
            saveLoginInfo();
           /* Intent login_intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(login_intent);
            finish();*/
            return true;
        }else {
            altdialog("아이디 비밀번호를 확인해주세요.","");
        }
        return false;
    }


    public void altdialog(String settitle , String setmessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle( settitle ).setMessage( setmessage );
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean vaildchk(){
        if( edt_id.getText().toString().equals("") ) {
            altdialog("아이디를 입력해주세요", "");
        }else if( edt_pw.getText().toString().equals("") ){
            altdialog("비밀번호를 입력해주세요", "");
        }else{
            return true;
        }

        return false;
    }

    public void getKakaoInfo(){
        UserApiClient.getInstance().me( (user, throwable) -> {
            if(throwable != null){
                // 오류임. 정보 못받아옴 ( Token이 없거나 Token을 삭제했을때(Logout)
                // KOE + 숫자
                Toast.makeText( LoginActivity.this , "오류 코드 : " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }else{
                // [ { } ] json 구조처럼 바로 데이터가 있는게 아님.
                // Account Object안에 List가 있거나 List안에 Object가 있는형식임.
                Account kakaoAcount = user.getKakaoAccount();
                if(kakaoAcount != null){
                    String email = kakaoAcount.getEmail();
                    AskTask task = new AskTask( CommonVal.httpip,"social_login.user");
                    task.addParam("id" , email);
                    InputStream in =  CommonMethod.excuteGet(task);
                    Gson gson = new Gson();
                    boolean data = gson.fromJson(new InputStreamReader(in) , Boolean.class);
                    String aa = "";
                    if( data ){
                        getinfo(email);
                    }else{
                        //아이디가 없음
                        altdialog("알림" ,"아이디가 없어 회원가입 화면으로 이동합니다.");
                        Intent intent = new Intent(LoginActivity.this , JoinMainActivity.class);
                        startActivity(intent);

                    }

                }

            }


            return null;
        });
    }




    public void getinfo(String email){
        //아이디가 있음
        if(email != null){
            //초대로 왔을 때


            CommonVal.curuser.setId( email );

            if (invite_title != null) {
                if(!in_family(invite_title)) {
                    AskTask invite_task = new AskTask(CommonVal.httpip, "invite_login.join");
                    FamilyInfoVO familyInfoVO = new FamilyInfoVO();
                    familyInfoVO.setTitle(invite_title);
                    familyInfoVO.setFamily_rels(invite_rels);
                    familyInfoVO.setId(CommonVal.curuser.getId());
                    Gson gson = new Gson();
                    invite_task.addParam("vo", gson.toJson(familyInfoVO));
                    InputStream invite_in = CommonMethod.excuteGet(invite_task);
                    boolean isSucc = gson.fromJson(new InputStreamReader(invite_in), Boolean.class);
                }
            }

            Intent intent = new Intent( LoginActivity.this , MainActivity.class);
            startActivity(intent);
            //아기 리스트 불러오기
            AskTask task_social = new AskTask(CommonVal.httpip, "list.bif");
            //로그인 정보로 수정 필요
            task_social.addParam("id", CommonVal.curuser.getId());
            InputStream in1 = CommonMethod.excuteGet(task_social);
            CommonVal.baby_list = gson.fromJson(new InputStreamReader(in1), new TypeToken<List<BabyInfoVO>>() {
            }.getType());
            if (CommonVal.baby_list.size() != 0) {
                CommonVal.curbaby = CommonVal.baby_list.get(0);
            }

            // 가족정보 불러오기
            task_social = new AskTask(CommonVal.httpip, "titlelist.us");
            task_social.addParam("id", CommonVal.curuser.getId());
            in1 = CommonMethod.excuteGet(task_social);
            CommonVal.family_title = gson.fromJson(new InputStreamReader(in1), new TypeToken<List<String>>() {
            }.getType());

            finish();
        }else{
            altdialog("아이디가 없습니다","");
        }

    }



}//Class