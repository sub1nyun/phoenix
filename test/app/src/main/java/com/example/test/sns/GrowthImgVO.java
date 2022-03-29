package com.example.test.sns;

import java.util.ArrayList;

public class GrowthImgVO {
    int gro_no;
    String filename, filepath;
    ArrayList<String> gro_img;



    public int getGro_no() {
        return gro_no;
    }

    public void setGro_no(int gro_no) {
        this.gro_no = gro_no;
    }

    public ArrayList<String> getGro_img() {
        return gro_img;
    }

    public void setGro_img(ArrayList<String> gro_img) {
        this.gro_img = gro_img;
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
}
