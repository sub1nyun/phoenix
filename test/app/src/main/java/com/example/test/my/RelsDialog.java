package com.example.test.my;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.test.R;

import java.util.ArrayList;

public class RelsDialog extends Dialog implements View.OnClickListener {
    private DialogListener dialogListener;

    Button btn_rels, my_close;
    String rels = "";
    ArrayList<Button> buttons = new ArrayList<>();
    public RelsDialog(@NonNull Context context, String rels) {
        super(context);
        this.rels = rels;
    }

    public interface DialogListener{
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

        if(rels.equals("엄마")){
            changeBtn(0);
        } else if(rels.equals("아빠")){
            changeBtn(1);
        } else if(rels.equals("시터")){
            changeBtn(2);
        } else if(rels.equals("할머니")){
            changeBtn(3);
        } else if(rels.equals("할아버지")){
            changeBtn(4);
        } else{
            changeBtn(5);
        }

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
                changeBtn(0);
                break;
            case R.id.rels_dad:
                rels = "아빠";
                changeBtn(1);
                break;
            case R.id.rels_sitter:
                rels = "시터";
                changeBtn(2);
                break;
            case R.id.rels_grmom:
                rels = "할머니";
                changeBtn(3);
                break;
            case R.id.rels_grdad:
                rels = "할아버지";
                changeBtn(4);
                break;
            case R.id.rels_other:
                rels = "기타";
                changeBtn(5);
                break;

        }
    }

    public void changeBtn(int index){
        for(int i=0; i<6; i++){
            if(i == index){
                (buttons.get(i)).setBackground(getContext().getDrawable(R.drawable.circle_btn_select));
                buttons.get(i).setTextColor(Color.parseColor("#ffffff"));
            } else{
                (buttons.get(i)).setBackground(getContext().getDrawable(R.drawable.circle_btn));
                buttons.get(i).setTextColor(Color.parseColor("#000000"));
            }
        }

    }
}