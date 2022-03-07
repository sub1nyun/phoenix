package com.example.test.sns;

public class SnsDTO {

    private String snsImg;
    private String snscomment;

    public SnsDTO(String snsImg, String snscomment) {
        this.snsImg = snsImg;
        this.snscomment = snscomment;
    }

    public String getSnsImg() {
        return snsImg;
    }

    public void setSnsImg(String snsImg) {
        this.snsImg = snsImg;
    }

    public String getSnscomment() {
        return snscomment;
    }

    public void setSnscomment(String snscomment) {
        this.snscomment = snscomment;
    }
}
