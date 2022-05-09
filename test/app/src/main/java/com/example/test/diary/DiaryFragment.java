package com.example.test.diary;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.my.BabyInfoVO;
import com.example.test.my.MyFragment;
import com.example.test.my.RelsDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class DiaryFragment extends Fragment implements  View.OnLongClickListener{
    ImageView imv_calender, imv_backday, imv_forwardday, imv_graph, imv_store, imv_invite, baby_img;
    RoundedImageView imv_baby;
    TextView tv_today, tv_baby_gender, tv_baby_name, tv_baby_age, tv_none;
    Intent intent;
    RecyclerView rcv_diary;


    final int CODE = 1000;

    ArrayList<ImageView> imv_list = new ArrayList<>();
    boolean[] ac_arr = new boolean[10];
    String[] cate_arr = {"모유", "체온", "기저귀", "수면", "분유", "이유식", "목욕", "물", "투약", "간식"};
    int[] cateimg_arr = {R.drawable.mou, R.drawable.temp, R.drawable.toilet, R.drawable.sleep, R.drawable.bunu, R.drawable.eat, R.drawable.bath, R.drawable.water, R.drawable.pills, R.drawable.danger};
    int[] cateac_arr = {R.drawable.mou_ac, R.drawable.temp_ac, R.drawable.toilet_ac, R.drawable.sleep_ac, R.drawable.bunu_ac, R.drawable.eat_ac, R.drawable.bath_ac, R.drawable.water_ac, R.drawable.pill_ac, R.drawable.danger_ac};

    DatePickerDialog.OnDateSetListener callbackMethod;

    Gson gson = new Gson();
    Calendar today = Calendar.getInstance();//오늘날짜 받기

    String pageDate; //페이지 날짜 저장하기

    public DiaryFragment() {

    }

    public DiaryFragment(String pageDate) {
        this.pageDate = pageDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_dairy, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        imv_calender = rootview.findViewById(R.id.imv_calender);
        tv_today = rootview.findViewById(R.id.tv_today);
        rcv_diary = rootview.findViewById(R.id.rcv_diary);
        tv_none = rootview.findViewById(R.id.tv_none);

        imv_baby = rootview.findViewById(R.id.imv_baby);

        tv_baby_gender = rootview.findViewById(R.id.tv_baby_gender);
        tv_baby_name = rootview.findViewById(R.id.tv_baby_name);
        tv_baby_age = rootview.findViewById(R.id.tv_baby_age);

        imv_list.add(rootview.findViewById(R.id.imv_mou));
        imv_list.add(rootview.findViewById(R.id.imv_temp));
        imv_list.add(rootview.findViewById(R.id.imv_toilet));
        imv_list.add(rootview.findViewById(R.id.imv_sleep));
        imv_list.add(rootview.findViewById(R.id.imv_bunu));
        imv_list.add(rootview.findViewById(R.id.imv_eat));
        imv_list.add(rootview.findViewById(R.id.imv_bath));
        imv_list.add(rootview.findViewById(R.id.imv_water));
        imv_list.add(rootview.findViewById(R.id.imv_pills));
        imv_list.add(rootview.findViewById(R.id.imv_danger));

        imv_backday = rootview.findViewById(R.id.imv_backday);
        imv_forwardday = rootview.findViewById(R.id.imv_forwardday);

        baby_img = rootview.findViewById(R.id.baby_img);
        imv_graph = rootview.findViewById(R.id.imv_graph);
        imv_store = rootview.findViewById(R.id.imv_store);
        imv_invite = rootview.findViewById(R.id.imv_invite);

        for(int i=0; i<ac_arr.length; i++){
            ac_arr[i] = false;
        }
        
        //개월수 구하기
        String baby_age_str = CommonVal.curbaby.getBaby_birth();
        String[] baby_age_arr = baby_age_str.substring(0,baby_age_str.indexOf(" ")).split("-");
        LocalDate theDate = LocalDate.of(Integer.parseInt(baby_age_arr[0]),Integer.parseInt(baby_age_arr[1]),Integer.parseInt(baby_age_arr[2]));

        if(theDate.isBefore(LocalDate.now())){
            Period age = theDate.until(LocalDate.now());
            tv_baby_age.setText(age.getYears()*12 + age.getMonths() + "개월 " + age.getDays() + "일");
        }else{
            tv_baby_age.setText("D - "+ LocalDate.now().until(theDate, ChronoUnit.DAYS));
        }

        tv_baby_name.setText(CommonVal.curbaby.getBaby_name());
        tv_baby_gender.setText(CommonVal.curbaby.getBaby_gender());

        if(CommonVal.curbaby.getBaby_photo() == null){
            imv_baby.setVisibility(View.GONE);
            if(CommonVal.curbaby.getBaby_gender().equals("여아")){
                baby_img.setImageResource(R.drawable.tmdwn_girl);
            }else{
                baby_img.setImageResource(R.drawable.tmdwn_boy);
            }
        } else{
            imv_baby.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(CommonVal.curbaby.getBaby_photo()).into(imv_baby);
        }

        //페이지 날짜를 넘겨받았을 때
        if(pageDate != null){
            String[] strDate = pageDate.split("-");
            today.set(Integer.parseInt(strDate[0]), Integer.parseInt(strDate[1]) - 1, Integer.parseInt(strDate[2]));
        }

        //Toast.makeText(getContext(), CommonVal.curbaby.getBaby_name(), Toast.LENGTH_SHORT).show();

        //그래프
        imv_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), GraphActivity.class);
                startActivity(intent);
            }
        });
        //키 / 체중 입력
        imv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BodyFragment();
                ((MainActivity)getActivity()).backFrag(new BodyFragment());
                ((MainActivity)getActivity()).changeFrag(fragment);
            }
        });
        //초대하기
        imv_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelsDialog dialog = new RelsDialog(getContext(),"엄마");
                dialog.show();
                dialog.setDialogListener(new RelsDialog.DialogListener() {
                    @Override
                    public void onPositiveClick(String name) {
                        createDynamicLink(name);
                    }
                });
            }
        });

        //탭도 옮기기-----------------------------
        baby_img.setOnClickListener(v -> {
            ((MainActivity)getActivity()).changeTab(4);
            ((MainActivity)getActivity()).changeFrag(new MyFragment());
        });

        //날짜 세팅함
        tv_today.setText(today.get(Calendar.YEAR) + "년 " + (today.get(Calendar.MONTH)+1) + "월 " + today.get(Calendar.DATE) + "일");

        //리스트 불러옴
        chgDateList(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE));

        //얘가 날짜값을 받아서 세팅해주는 역할
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                today.set(year, month, dayOfMonth);
                tv_today.setText(year + "년 " + (month+1) + "월 " + dayOfMonth + "일");
                chgDateList(year,month,dayOfMonth);
                setCateImage(10);
            }
        };

        //하루전
        imv_backday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.add(Calendar.DATE, -1);
                tv_today.setText(today.get(Calendar.YEAR) + "년 " + (today.get(Calendar.MONTH)+1) + "월 " + today.get(Calendar.DATE) + "일");
                chgDateList(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE));
                setCateImage(10);
            }
        });
        //하루후
        imv_forwardday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.add(Calendar.DATE, 1);
                tv_today.setText(today.get(Calendar.YEAR) + "년 " + (today.get(Calendar.MONTH)+1) + "월 " + today.get(Calendar.DATE) + "일");
                chgDateList(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE));
                setCateImage(10);
            }
        });

        //날짜선택할 다이얼로그 띄워줌
        imv_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
                dialog.show();

            }
        });


        //아기상태 버튼 클릭 시
        imv_list.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("목욕"));
                getActivity().startActivityForResult(intent, CODE);
                //startActivity(intent);
            }
        });
        imv_list.get(6).setOnLongClickListener(this);
        imv_list.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("체온"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_list.get(1).setOnLongClickListener(this);
        imv_list.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("수면"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_list.get(3).setOnLongClickListener(this);
        imv_list.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("이유식"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_list.get(5).setOnLongClickListener(this);
        imv_list.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("기저귀"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_list.get(2).setOnLongClickListener(this);
        imv_list.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("투약"));
                getActivity().startActivityForResult(intent, CODE);

            }
        });
        imv_list.get(8).setOnLongClickListener(this);
        imv_list.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("모유"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_list.get(0).setOnLongClickListener(this);
        imv_list.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("분유"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_list.get(4).setOnLongClickListener(this);
        imv_list.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("물"));
                getActivity().startActivityForResult(intent, CODE);

            }
        });
        imv_list.get(7).setOnLongClickListener(this);
        imv_list.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("간식"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_list.get(9).setOnLongClickListener(this);

        return rootview;
    }

    public boolean onLongClick(View v){
        switch (v.getId()){
            case R.id.imv_mou:
                setCateImage(0);
                break;
            case R.id.imv_temp:
                setCateImage(1);
                break;
            case R.id.imv_toilet:
                setCateImage(2);
                break;
            case R.id.imv_sleep:
                setCateImage(3);
                break;
            case R.id.imv_bunu:
                setCateImage(4);
                break;
            case R.id.imv_eat:
                setCateImage(5);
                break;
            case R.id.imv_bath:
                setCateImage(6);
                break;
            case R.id.imv_water:
                setCateImage(7);
                break;
            case R.id.imv_pills:
                setCateImage(8);
                break;
            case R.id.imv_danger:
                setCateImage(9);
                break;
        }
        return true;
    }

    public void setCateImage(int n){
        for(int i=0; i<ac_arr.length; i++){
            if(i == n){
                if(ac_arr[i]){
                    chgDateList(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
                    imv_list.get(i).setImageResource(cateimg_arr[i]);
                    ac_arr[i] = false;
                }else{
                    chgCateList(cate_arr[i]);
                    imv_list.get(i).setImageResource(cateac_arr[i]);
                    ac_arr[i] = true;
                }
            }else{
                imv_list.get(i).setImageResource(cateimg_arr[i]);
                ac_arr[i] = false;
            }
        }
    }
    public DiaryVO setDTO(String category){
        DiaryVO vo = new DiaryVO();
        vo.setBaby_id(CommonVal.curbaby.getBaby_id());
        vo.setBaby_category(category);
        String strM = today.get(Calendar.MONTH) < 9 ? "0"+(today.get(Calendar.MONTH)+1) : ""+(today.get(Calendar.MONTH)+1);
        String strD = today.get(Calendar.DATE) < 10 ? "0"+today.get(Calendar.DATE) : ""+today.get(Calendar.DATE);
        vo.setDiary_date(today.get(Calendar.YEAR) + "-" + strM + "-" + strD);
        return vo;
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what == 1){
                DiaryAdapter adapter = new DiaryAdapter((List<DiaryVO>) msg.obj, getContext() );
                if(((List<DiaryVO>) msg.obj).size() == 0){
                    tv_none.setVisibility(View.VISIBLE);
                }
                else{
                    tv_none.setVisibility(View.GONE);
                }
                rcv_diary.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                rcv_diary.setLayoutManager(manager);

            }
        }
    };

    public void chgDateList(int y, int m, int d){
        List<DiaryVO> list;
        AskTask task = new AskTask(CommonVal.httpip,"list.di");
        task.addParam("date", y + "-" + (m+1) + "-" + d);

        task.addParam("id", CommonVal.curbaby.getBaby_id());
        InputStream in = CommonMethod.excuteGet(task);
        if(in != null){
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<DiaryVO>>(){}.getType());
            Message msg = handler.obtainMessage(1, list);

            handler.sendMessage(msg);
        }
    }

    public void chgCateList(String category){
        List<DiaryVO> list;
        AskTask task = new AskTask(CommonVal.httpip,"list_cate.di");
        task.addParam("date", today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH)+1) + "-" + today.get(Calendar.DATE));
        task.addParam("id", CommonVal.curbaby.getBaby_id());
        task.addParam("baby_category", category);
        InputStream in = CommonMethod.excuteGet(task);
        if(in != null){
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<DiaryVO>>(){}.getType());
            Message msg = handler.obtainMessage(1, list);
            handler.sendMessage(msg);
        }
    }

    private void createDynamicLink(String rels) {
        List<String> temp = new ArrayList<>();
        for(int i=0; i<CommonVal.baby_list.size(); i++){
            if(CommonVal.curbaby.getTitle().equals(CommonVal.baby_list.get(i).getTitle())) {
                if(CommonVal.baby_list.get(i).getBaby_photo() != null)
                    temp.add(CommonVal.baby_list.get(i).getBaby_photo());
            }
        }

        Gson gson = new Gson();
        String data = gson.toJson(temp);

        String familyId = CommonVal.curbaby.getBaby_id();
        String invitationLink = "https://babysmilesupport.page.link/invite?"+"rels="+rels+"&familyId="+familyId+"&data="+data; //생성할 다이나믹 링크

        Uri imageUri = (new Uri.Builder())
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(R.drawable.ic_launcher_background))
                .appendPath(getResources().getResourceTypeName(R.drawable.ic_launcher_background))
                .appendPath(getResources().getResourceEntryName(R.drawable.ic_launcher_background))
                .build();

        FirebaseDynamicLinks.getInstance().createDynamicLink().setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setTitle("BSS").setDescription("함께해보실래요?").setImageUrl(Uri.parse("https://postfiles.pstatic.net/MjAyMjA1MDRfMTMy/MDAxNjUxNjM2ODU4MjM5.xUpGluedjwd4ZCDla7u1WeTw_pa_kob11Hk_cpXXQPEg.nY45cQNWAoeM4H4EFbhjcj6OH9F7Y6jSTyVD1qZGTyEg.PNG.tmdwn4645/invite_logo.png?type=w580")).build())
                .setLink(Uri.parse(invitationLink))    //정보를 담는 json 사이트를 넣자!!
                .setDomainUriPrefix("https://babysmilesupport.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .buildShortDynamicLink().addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
                    @Override
                    public void onSuccess(ShortDynamicLink shortDynamicLink) {
                        send(shortDynamicLink.getShortLink());
                        //kakaoShare(shortDynamicLink.getShortLink());
                    }
                });
    }
    public void send(Uri shortDynamicLink){
        Intent Sharing_intent = new Intent(Intent.ACTION_SEND);
        Sharing_intent.setType("text/plain");
        String Test_Message = shortDynamicLink.toString();
//        Sharing_intent.putExtra(Intent.EXTRA_SUBJECT, );
        Sharing_intent.putExtra(Intent.EXTRA_TEXT, "BSS의 공동양육자로 초대되셨습니다. 함께 육아일기를 작성해보세요!\n" + Test_Message);
        Intent Sharing = Intent.createChooser(Sharing_intent, "공동양육자를 초대해보세요!");
        startActivity(Sharing);
    }

}
