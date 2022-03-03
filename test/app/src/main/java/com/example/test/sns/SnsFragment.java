package com.example.test.sns;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;


public class SnsFragment extends Fragment {

    public SnsFragment() {
    }

    ViewPager2 snspager;
    ArrayList<SnsDTO> snslist = new ArrayList<>();
    ImageView sns_plus, sns_more, testImg;
    Intent intent;
    DotsIndicator dotsIndicator;

    String imgFilePath = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sns, container, false);

        snspager = rootView.findViewById(R.id.sns_view_pager);
        dotsIndicator = rootView.findViewById(R.id.sns_indicator);
        sns_plus = rootView.findViewById(R.id.sns_plus);
        sns_more = rootView.findViewById(R.id.sns_more);
        testImg = rootView.findViewById(R.id.testImg);





       snslist.add(new SnsDTO(R.drawable.sns_test4, "테스트1"));
       snslist.add(new SnsDTO(R.drawable.sns_test3, "테스트2"));
       snslist.add(new SnsDTO(R.drawable.sns_test2, "테스트3"));
       snslist.add(new SnsDTO(R.drawable.sns_test, "테스트4"));

       SnsViewPagerAdapter snsadapter = new SnsViewPagerAdapter(inflater,snslist);
       snspager.setAdapter(snsadapter);
       dotsIndicator.setViewPager2(snspager);

        PopupMenu popupMenu = new PopupMenu(getContext(), rootView);
        popupMenu.getMenuInflater().inflate(R.menu.sns_submenu, popupMenu.getMenu());



       sns_plus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                intent = new Intent(getContext(), SnsNewActivity.class);
                startActivity(intent);
           }
       });




       sns_more.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                popupMenu.show();
           }
       });

       popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {

               if(item.getItemId() == R.id.sns_new_edit) {
                   intent = new Intent(getContext(), SnsNewActivity.class);
                   startActivity(intent);
               }else if(item.getItemId() == R.id.sns_new_delete) {
                    Sns_Delete_Dialog delete_dialog = new Sns_Delete_Dialog(getContext());
                    delete_dialog.show();

               }

               return false;
           }
       });


//        imgFilePath = intent.getStringExtra("path");
//        Glide.with(this).load(imgFilePath).into(testImg);






        return rootView;
    }
}