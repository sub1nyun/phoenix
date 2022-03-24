package com.example.test.sns;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;


public class SnsVO implements Serializable {

    private ArrayList<String> sns_img;
    private String sns_content;
    private int sns_no;
    private Date sns_date;
    private String id, title;

    public SnsVO() {

    }

    public SnsVO(ArrayList<String> sns_img, String sns_content) {
        this.sns_img = sns_img;
        this.sns_content = sns_content;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getSns_img() {
        return sns_img;
    }

    public void setSns_img(ArrayList<String> sns_img) {
        this.sns_img = sns_img;
    }

    public String getSns_content() {
        return sns_content;
    }

    public void setSns_content(String sns_content) {
        this.sns_content = sns_content;
    }

    public int getSns_no() {
        return sns_no;
    }

    public void setSns_no(int sns_no) {
        this.sns_no = sns_no;
    }

    public Date getSns_date() {
        return sns_date;
    }

    public void setSns_date(Date sns_date) {
        this.sns_date = sns_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
