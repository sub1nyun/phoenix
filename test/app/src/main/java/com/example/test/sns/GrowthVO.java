package com.example.test.sns;

import java.util.ArrayList;

public class GrowthVO {

    int gro_no;
    String gro_date;
    String gro_content, baby_name, baby_gender, filename, baby_id, b_id ;
    ArrayList<String> imgList = new ArrayList<String>();
    String gro_img ;








    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = this.baby_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getGro_img() {
        return gro_img;
    }

    public void setGro_img(String gro_img) {
        this.gro_img = gro_img;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(String imgList) {
        this.imgList.add(imgList);
    }

    public int getGro_no() {
        return gro_no;
    }

    public void setGro_no(int gro_no) {
        this.gro_no = gro_no;
    }

    public String getGro_date() {
        return gro_date;
    }

    public void setGro_date(String gro_date) {
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





    public String getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(String baby_id) {
        this.baby_id = baby_id;
    }

}
