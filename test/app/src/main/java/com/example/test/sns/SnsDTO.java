package com.example.test.sns;

public class SnsDTO {

    private int snsImg;
    private String snscomment;

    public SnsDTO(int snsImg, String snscomment) {
        this.snsImg = snsImg;
        this.snscomment = snscomment;
    }

    public int getSnsImg() {
        return snsImg;
    }

    public void setSnsImg(int snsImg) {
        this.snsImg = snsImg;
    }

    public String getSnscomment() {
        return snscomment;
    }

    public void setSnscomment(String snscomment) {
        this.snscomment = snscomment;
    }
}
