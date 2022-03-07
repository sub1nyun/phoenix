package com.example.test.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.test.R;

public class DetailActivity extends AppCompatActivity {
    Button btn_cancel, btn_save;
    TextView tv_start,tv_end;
    EditText edt_memo;
    TimePickerDialog.OnTimeSetListener callbackMethod1, callbackMethod2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
        tv_start = findViewById(R.id.tv_start);
        tv_end = findViewById(R.id.tv_end);
        edt_memo = findViewById(R.id.edt_memo);

        //현재시간 설정
        Intent intent = getIntent();
        detailDTO dto = (detailDTO) intent.getSerializableExtra("dto");
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