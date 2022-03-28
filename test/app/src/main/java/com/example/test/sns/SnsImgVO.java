package com.example.test.sns;

import java.util.ArrayList;

public class SnsImgVO {
    String id, title;
    int sns_no;
    ArrayList<String> sns_img;
    String filename, filepath;

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

    public ArrayList<String> getSns_img() {
        return sns_img;
    }

    public void setSns_img(ArrayList<String> sns_img) {
        this.sns_img = sns_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSns_no() {
        return sns_no;
    }

    public void setSns_no(int sns_no) {
        this.sns_no = sns_no;
    }
}
