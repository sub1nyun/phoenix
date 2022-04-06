package com.example.test.join;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kakao.sdk.common.KakaoSdk;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.oauth.view.NidOAuthLoginButton;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfileResponse;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class UserFragment extends Fragment {
    EditText edt_id, edt_pw, edt_pwchk;
    ImageView join_kakao;
    TextView tv_id_check, auto_id_check, pw_check, pwchk_check;
    String family_id;
    Gson gson = new Gson();
    NidOAuthLoginButton join_naver;
    public static OAuthLogin mOAuthLoginModule;

    public UserFragment() {

    }

    public UserFragment(String family_id) {
        this.family_id = family_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootVIew = (ViewGroup) inflater.inflate(R.layout.fragment_user, container, false);
        JoinMainActivity.go = 1;
        /*new Handler().postDelayed(new Runnable() {        딜레이 주는거
            @Override
            public void run() {
                codechk();
            }
        }, 500);*/


        KakaoSdk.init(getContext() ,"884cf31c300f60971b6a3d015d8c005e");
        NaverIdLoginSDK.INSTANCE.initialize( getContext() ,"uR4I8FNC11hwqTB3Fr6l","U3LRpxH6Tq","BSS");


        if (family_id != null) {
            JoinMainActivity.go = 7;
        }

        edt_id = rootVIew.findViewById(R.id.edt_id);
        edt_pw = rootVIew.findViewById(R.id.edt_pw);
        edt_pwchk = rootVIew.findViewById(R.id.edt_pwchk);
        //tv_id_check = rootVIew.findViewById(R.id.tv_id_check);
        auto_id_check = rootVIew.findViewById(R.id.id_check);
        pw_check = rootVIew.findViewById(R.id.pw_check);
        pwchk_check = rootVIew.findViewById(R.id.pwchk_check);

        join_kakao = rootVIew.findViewById(R.id.join_kakao);
        join_naver = rootVIew.findViewById(R.id.join_naver);


        edt_id.requestFocus();

        join_kakao.setOnClickListener(v -> {
            Toast.makeText(getContext(), "카카오 눌림", Toast.LENGTH_SHORT).show();
        });



        naverLogin();


        edt_id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER){
                    String aa= "";
                    return true;
                }
                return false;
            }
        });
        id_nospace();
        pw_nospace();
        pwchk_nospace();
        edt_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.vo.setId(edt_id.getText().toString());
                id_valid();
                id_nospace();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.vo.setPw(edt_pw.getText().toString());
                pw_valid();
                pw_nospace();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_pwchk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.vo.setPw_chk(edt_pwchk.getText().toString());
                pwchk_valid();
                pwchk_nospace();
                valid_result();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        return rootVIew;
    }//onCreateView


    //중복확인
    public boolean id_check() {
        AskTask task = new AskTask(CommonVal.httpip, "id_check.join");
        task.addParam("id", JoinMainActivity.vo.getId());
        String aa = "";
        InputStream in = CommonMethod.excuteGet(task);
        boolean data = gson.fromJson(new InputStreamReader(in), new TypeToken<Boolean>() {
        }.getType());
        return data;
    }

    //id유효성 ^[a-z0-9]*$r1
    public boolean id_valid() {
        if (edt_id.getText().toString().equals("")) {
            auto_id_check.setText("아이디를 입력해주세요.");
            auto_id_check.setTextColor(Color.parseColor("#FF4A4A"));
            return false;
        } else if (Pattern.matches("^[0-9]{1,28}+[a-z0-9]{1,28}$", JoinMainActivity.vo.getId()) || Pattern.matches("^[a-z]{1,28}+[a-z0-9]{1,28}$", JoinMainActivity.vo.getId())) {
            //"(?:\\w{4,10}[a-z][0-9]+)$"
            auto_id_check.setText("사용가능한 ID 형식입니다.");
            auto_id_check.setTextColor(Color.parseColor("#46FF3E"));
            if (id_check()) {
                auto_id_check.setText("사용 가능한 아이디 입니다.");
                auto_id_check.setTextColor(Color.parseColor("#46FF3E"));
                return true;
            } else {
                auto_id_check.setText("이미사용중인 아이디 입니다.");
                auto_id_check.setTextColor(Color.parseColor("#FF4A4A"));
            }
        } else {
            auto_id_check.setText("ID는 영어 소문자/숫자를 5~10글자 입력하셔야합니다.");
            auto_id_check.setTextColor(Color.parseColor("#FF4A4A"));
            return false;
        }

        return false;
    }


    //pw유효성
    public boolean pw_valid() {
        if (edt_pw.getText().toString().equals("")) {
            pw_check.setText("비밀번호를 입력해주세요.");
            pw_check.setTextColor(Color.parseColor("#FF4A4A"));
            return false;
        } else if (Pattern.matches("^[0-9]{1,28}+[a-z0-9]{1,28}$", JoinMainActivity.vo.getPw()) || Pattern.matches("^[a-z]{1,28}+[a-z0-9]{1,28}$", JoinMainActivity.vo.getPw())) {
            pw_check.setText("사용 가능한 비밀번호입니다.");
            pw_check.setTextColor(Color.parseColor("#46FF3E"));
            return true;
        }else {
            pw_check.setText("PW는 영어 소문자/숫자를 5~10글자 입력하셔야합니다.");
            pw_check.setTextColor(Color.parseColor("#FF4A4A"));
        }
        return false;
    }

    public boolean pwchk_valid(){
        if( edt_pwchk.getText().toString().equals("")){
            pwchk_check.setText("비밀번호를 입력하세요.");
            pwchk_check.setTextColor(Color.parseColor("#FF4A4A"));
        }else if( edt_pwchk.getText().toString().equals(edt_pw.getText().toString())){
            pwchk_check.setText("비밀번호가 일치합니다.");
            pwchk_check.setTextColor(Color.parseColor("#46FF3E"));
            return true;
        }else if( !edt_pw.getText().toString().equals(edt_pwchk.getText().toString())){
            pwchk_check.setText("비밀번호가 다릅니다.");
            pwchk_check.setTextColor(Color.parseColor("#FF4A4A"));
            return false;
        }
        return false;
    }

    public boolean valid_result(){
        if(  pw_valid() && pwchk_valid() ){
            JoinMainActivity.result = 1;
            return true;
        }else {
            JoinMainActivity.result = 0;
        }
        return false;
    }


    public void id_nospace(){
        edt_id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_SPACE || keyCode == event.KEYCODE_ENTER){
                    String aa= "";
                    Toast.makeText(getContext(), "공백은 입력할수 없습니다.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
    public void pw_nospace(){
        edt_pw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_SPACE || keyCode == event.KEYCODE_ENTER){
                    String aa= "";
                    Toast.makeText(getContext(), "공백은 입력할수 없습니다.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
    public void pwchk_nospace(){
        edt_pwchk.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_SPACE || keyCode == event.KEYCODE_ENTER){
                    String aa= "";
                    Toast.makeText(getContext(), "공백은 입력할수 없습니다.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }


    public void naverLogin(){
        NidOAuthLogin authLogin = new NidOAuthLogin();
        join_naver.setOAuthLoginCallback(new OAuthLoginCallback() {
            @Override
            public void onSuccess() {
                Log.d("naver" ,"onSuccess:성공");
                Log.d("naver", NaverIdLoginSDK.INSTANCE.getAccessToken());
                authLogin.callProfileApi(new NidProfileCallback<NidProfileResponse>() {
                    @Override
                    public void onSuccess(NidProfileResponse nidProfileResponse) {
                        Log.d("naver","onSuccess:성공" + nidProfileResponse.getProfile().getEmail());
                        JoinMainActivity.vo.setId( nidProfileResponse.getProfile().getEmail() );
                        JoinMainActivity.vo.setNaver_id( "Y" );
                        //edt_id.setEnabled(false); edit 막기
                        altdialog("네이버 아이디로 시작합니다.", "이름     : " + nidProfileResponse.getProfile().getName() +"\n" + "아이디 : " +JoinMainActivity.vo.getId() );
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

    public void altdialog(String settitle , String setmessage){
        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
        builder.setTitle( settitle ).setMessage( setmessage );
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                JoinMainActivity act = (JoinMainActivity) getActivity();
                act.socialNaver();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }









}