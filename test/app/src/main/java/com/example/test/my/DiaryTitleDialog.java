package com.example.test.my;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.test.R;
import com.example.test.common.CommonVal;
import java.util.List;

public class DiaryTitleDialog extends Dialog {
    private DialogListener dialogListener;
    ImageView diary_title_close;
    EditText diary_title_edit;
    Button diary_title_ok;
    String title;
    Boolean check = false;
    TextView check_title;

    public DiaryTitleDialog(@NonNull Context context, String title) {
        super(context);
        this.title = title;
    }

    interface DialogListener{
        void onPositiveClick(String name);
    }

    public void setDialogListener(DialogListener dialogListener){ this.dialogListener = dialogListener; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_diary_title);
        diary_title_close = findViewById(R.id.diary_title_close);
        diary_title_edit = findViewById(R.id.diary_title_edit);
        diary_title_ok = findViewById(R.id.diary_title_ok);
        check_title = findViewById(R.id.check_title);

        List<String> list = CommonVal.family_title;

        diary_title_edit.setText(title);

        diary_title_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        diary_title_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(int i=0; i<list.size(); i++){
                    if((!list.get(i).equals(s.toString().trim()) && s.toString().trim().length()>0) || title.equals(s.toString().trim())){
                        check_title.setText("사용할 수 있는 제목입니다.");
                        check_title.setTextColor(Color.parseColor("#03fc1c"));
                        check = true;
                    } else{
                        check_title.setText("동일한 제목이 존재합니다");
                        check_title.setTextColor(Color.parseColor("#fc0303"));
                        check = false;
                        break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        diary_title_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check){
                    dialogListener.onPositiveClick(diary_title_edit.getText().toString().trim());
                    dismiss();
                } else {

                }
            }
        });
    }
}