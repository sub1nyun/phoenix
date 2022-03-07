package com.example.test.my;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.test.MainActivity;
import com.example.test.R;

public class DiaryTitleDialog extends Dialog {
    private DialogListener dialogListener;
    ImageView diary_title_close;
    EditText diary_title_edit;
    Button diary_title_ok;
    public DiaryTitleDialog(@NonNull Context context) {
        super(context);
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

        //넘어온 값으로 edittext 변경

        diary_title_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        diary_title_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onPositiveClick(diary_title_edit.getText().toString().trim());
                dismiss();
            }
        });
    }
}