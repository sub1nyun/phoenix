package com.example.test.my;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.test.R;

import java.util.ArrayList;

public class RelsDialog extends Dialog implements View.OnClickListener {
    private DialogListener dialogListener;
    //Button rels_mom, rels_dad, rels_sitter, rels_grmom, rels_grdad, rels_other;
    ImageView my_close;
    Button btn_rels;
    String rels = "";
    //GradientDrawable gradientDrawable;
    ArrayList<Button> buttons = new ArrayList<>();
    public RelsDialog(@NonNull Context context) {
        super(context);
    }

    interface DialogListener{
        void onPositiveClick(String name);
    }

    public void setDialogListener(DialogListener dialogListener){ this.dialogListener = dialogListener; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rels);

        my_close = findViewById(R.id.my_close);
        btn_rels = findViewById(R.id.btn_rels);

        buttons.add(findViewById(R.id.rels_mom));
        buttons.add(findViewById(R.id.rels_dad));
        buttons.add(findViewById(R.id.rels_sitter));
        buttons.add(findViewById(R.id.rels_grmom));
        buttons.add(findViewById(R.id.rels_grdad));
        buttons.add(findViewById(R.id.rels_other));

        my_close.setOnClickListener(this);
        btn_rels.setOnClickListener(this);

        buttons.get(0).setOnClickListener(this);
        buttons.get(1).setOnClickListener(this);
        buttons.get(2).setOnClickListener(this);
        buttons.get(3).setOnClickListener(this);
        buttons.get(4).setOnClickListener(this);
        buttons.get(5).setOnClickListener(this);

        //현재 받아온 값만 하이라이트 처리, 나머지는 디폴트로로
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_close:
                //설정값 초기화 코드 추가
                dismiss();
                break;
            case R.id.btn_rels:
                dialogListener.onPositiveClick(rels);
                dismiss();
                break;
            case R.id.rels_mom:
                rels = "엄마";
                chageBtn(0);
                break;
            case R.id.rels_dad:
                rels = "아빠";
                chageBtn(1);
                break;
            case R.id.rels_sitter:
                rels = "시터";
                chageBtn(2);
                break;
            case R.id.rels_grmom:
                rels = "할머니";
                chageBtn(3);
                break;
            case R.id.rels_grdad:
                rels = "할아버지";
                chageBtn(4);
                break;
            case R.id.rels_other:
                rels = "기타";
                chageBtn(5);
                break;

        }
    }

    public void chageBtn(int index){

        for(int i=0; i<6; i++){
            if(i == index){
                //GradientDrawable gradientDrawable = gradientDrawable = (GradientDrawable) buttons.get(i).getBackground();
                //gradientDrawable.setColor(Color.parseColor("#ff9999"));
                (buttons.get(i)).setBackground(getContext().getDrawable(R.drawable.circle_btn_select));
                buttons.get(i).setTextColor(Color.parseColor("#ffffff"));
            } else{
                //GradientDrawable gradientDrawable = gradientDrawable = (GradientDrawable) buttons.get(i).getBackground();
                //gradientDrawable.setColor(Color.parseColor("#ffffff"));
                (buttons.get(i)).setBackground(getContext().getDrawable(R.drawable.circle_btn));
                buttons.get(i).setTextColor(Color.parseColor("#000000"));
            }
        }

    }
}