package com.example.test.common;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BreakText extends androidx.appcompat.widget.AppCompatTextView{
//텍스트 문자를 단어-> 글자 단위로 변경

    public BreakText(@NonNull Context context) {
        super(context);
    }

    public BreakText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text.toString().replace("","\u00A0"), type);
    }

    public BreakText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);











    }
}
