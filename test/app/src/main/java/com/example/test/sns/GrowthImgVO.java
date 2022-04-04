package com.example.test.sns;

import java.util.ArrayList;

public class GrowthImgVO {
    int gro_no;
    String filename, baby_id;
    ArrayList<String> gro_img;





    public String getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(String baby_id) {
        this.baby_id = baby_id;
    }

    public ArrayList<String> getGro_img() {
        return gro_img;
    }

    public void setGro_img(ArrayList<String> gro_img) {
        this.gro_img = gro_img;
    }

    public int getGro_no() {
        return gro_no;
    }

    public void setGro_no(int gro_no) {
        this.gro_no = gro_no;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}