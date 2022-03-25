package com.example.test.join;

public class UserVO {
    private String title, family_rels, id, pw,pw_chk, naver_id, kakao_id;


    public String getPw_chk() {
        return pw_chk;
    }

    public void setPw_chk(String pw_chk) {
        this.pw_chk = pw_chk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFamily_rels() {
        return family_rels;
    }

    public void setFamily_rels(String family_rels) {
        this.family_rels = family_rels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNaver_id() {
        return naver_id;
    }

    public void setNaver_id(String naver_id) {
        this.naver_id = naver_id;
    }

    public String getKakao_id() {
        return kakao_id;
    }

    public void setKakao_id(String kakao_id) {
        this.kakao_id = kakao_id;
    }


}