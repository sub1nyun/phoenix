package com.example.test.diary;

import java.io.Serializable;
import java.sql.Date;

public class DiaryVO implements Serializable {
    int diary_id;
    String baby_id;
    Date diary_date;
    int amount;
    String start_time;
    double temperature;
    String memo;
    String end_time;
    String baby_category;
    String diary_type;

    int img;



    public DiaryVO() {

    }

    public DiaryVO(String baby_category, String start_time, int img) {
        this.baby_category = baby_category;
        this.start_time = start_time;
        this.img = img;
    }

    public int getDiary_id() {
        return diary_id;
    }
    public void setDiary_id(int diary_id) {
        this.diary_id = diary_id;
    }
    public String getBaby_id() {
        return baby_id;
    }
    public void setBaby_id(String baby_id) {
        this.baby_id = baby_id;
    }
    public Date getDiary_date() {
        return diary_date;
    }
    public void setDiary_date(Date diary_date) {
        this.diary_date = diary_date;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getStart_time() {
        return start_time;
    }
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getEnd_time() {
        return end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public String getBaby_category() {
        return baby_category;
    }
    public void setBaby_category(String baby_category) {
        this.baby_category = baby_category;
    }
    public String getDiary_type() {
        return diary_type;
    }
    public void setDiary_type(String diary_type) {
        this.diary_type = diary_type;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
