package com.example.test.my;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.test.MainActivity;
import com.example.test.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EditFragment extends Fragment {
    Button my_rels, cur_kg_btn, cur_cm_btn;
    TextView edit_birth;
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("JST"));
    private  DatePickerDialog.OnDateSetListener callbackMethod;
    private TimePickerDialog.OnTimeSetListener callbackTime;
    int y=0, m=0, d=0, h=0, mi=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_edit, container, false);

        my_rels = rootView.findViewById(R.id.my_rels);
        edit_birth = rootView.findViewById(R.id.edit_birth);
        cur_kg_btn = rootView.findViewById(R.id.cur_kg_btn);
        cur_cm_btn = rootView.findViewById(R.id.cur_cm_btn);

        my_rels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                RelsDialog dialog = new RelsDialog(getContext());
                //현재 관계 넘기기
                dialog.show();
                dialog.setDialogListener(new RelsDialog.DialogListener() {
                    @Override
                    public void onPositiveClick(String name) {
                        my_rels.setText(name);
                    }
                });
            }
        });

        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                y = year;
                m = monthOfYear + 1;
                d = dayOfMonth;

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                String time = sdf.format(new Date(System.currentTimeMillis()));
                String times[] = new String[2];
                times = time.split(":");
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), callbackTime, Integer.parseInt(times[0]), Integer.parseInt(times[1]), true);
                timePickerDialog.show();
            }
        };

        callbackTime = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                h = hourOfDay;
                mi = minute;
                edit_birth.setText(y + "년 " + m + "월 " + d + "일 " + h + "시 " + mi + "분");
            }
        };

        edit_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), callbackMethod, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });

        cur_kg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new BodyFragment());
            }
        });

        cur_cm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new BodyFragment());
            }
        });

        return rootView;
    }
}