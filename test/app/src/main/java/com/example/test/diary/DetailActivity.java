package com.example.test.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.test.R;

public class DetailActivity extends AppCompatActivity {
    Button btn_cancel, btn_save, btn1, btn2, btn3, btn4;
    TextView tv_start,tv_end, tv_date, tv_state;
    EditText edt_memo;
    TimePickerDialog.OnTimeSetListener callbackMethod1, callbackMethod2;
    LinearLayout linear_start, linear_end, linear_amount, linear_temp, linear_many;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
        tv_start = findViewById(R.id.tv_start);
        tv_end = findViewById(R.id.tv_end);
        edt_memo = findViewById(R.id.edt_memo);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        tv_date = findViewById(R.id.tv_date);
        tv_state = findViewById(R.id.tv_state);

        linear_start = findViewById(R.id.linear_start);
        linear_end = findViewById(R.id.linear_end);
        linear_amount = findViewById(R.id.linear_amount);
        linear_temp = findViewById(R.id.linear_temp);
        linear_many = findViewById(R.id.linear_many);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_memo.getWindowToken(), 0);


        //플래그먼트에서 dto 받기
        Intent intent = getIntent();
        detailDTO dto = (detailDTO) intent.getSerializableExtra("dto");

        tv_state.setText(dto.getState());
        tv_date.setText(dto.getDate());

        if((dto.getState()).equals("모유")){
            linear_amount.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            btn1.setText("왼쪽");
            btn2.setText("오른쪽");
            btn3.setText("둘다");
            btn4.setText("모름");
        }else if((dto.getState()).equals("분유")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getState()).equals("이유식")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getState()).equals("기저귀")){
            linear_amount.setVisibility(View.GONE);
            linear_end.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            btn1.setText("대변");
            btn2.setText("소변");
            btn3.setText("둘다");
            btn3.setVisibility(View.GONE);
        }else if((dto.getState()).equals("수면")){
            linear_amount.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            btn1.setText("낮잠");
            btn2.setText("밤잠");
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
        }else if((dto.getState()).equals("목욕")){
            linear_amount.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getState()).equals("체온")){
            linear_end.setVisibility(View.GONE);
            linear_amount.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getState()).equals("물")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getState()).equals("투약")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getState()).equals("간식")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }

        String[] time = (dto.getStart_time()).split(":");
        tv_start.setText(time[0] + "시" + time[1] + "분");
        tv_end.setText(time[0] + "시" + time[1] + "분");

        callbackMethod1 = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                dto.setStart_time(hourOfDay + ":" + minute);
                tv_start.setText(hourOfDay + "시" + minute + "분");
            }
        };

        callbackMethod2 = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                dto.setEnd_time(hourOfDay + ":" + minute);
                tv_end.setText(hourOfDay + "시" + minute + "분");
            }
        };

        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(DetailActivity.this, callbackMethod1, Integer.parseInt(time[0]), Integer.parseInt(time[1]), true);
                dialog.show();
            }
        });

        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(DetailActivity.this, callbackMethod2, Integer.parseInt(time[0]), Integer.parseInt(time[1]), true);
                dialog.show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto.setMemo(edt_memo.getText()+"");
                Intent intent = new Intent();
                intent.putExtra("dto", dto);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}