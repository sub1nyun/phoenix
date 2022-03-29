package com.example.test.sns;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;

public class GrowthVO {

    int gro_no;
    Date gro_date;
    String gro_content, baby_name, baby_gender, filename, filepath, baby_id;
    ArrayList<String> imgList;

    public int getGro_no() {
        return gro_no;
    }

    public void setGro_no(int gro_no) {
        this.gro_no = gro_no;
    }

    public Date getGro_date() {
        return gro_date;
    }

    public void setGro_date(Date gro_date) {
        this.gro_date = gro_date;
    }

    public String getGro_content() {
        return gro_content;
    }

    public void setGro_content(String gro_content) {
        this.gro_content = gro_content;
    }

    public String getBaby_name() {
        return baby_name;
    }

    public void setBaby_name(String baby_name) {
        this.baby_name = baby_name;
    }

    public String getBaby_gender() {
        return baby_gender;
    }

    public void setBaby_gender(String baby_gender) {
        this.baby_gender = baby_gender;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(String baby_id) {
        this.baby_id = baby_id;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }
}
