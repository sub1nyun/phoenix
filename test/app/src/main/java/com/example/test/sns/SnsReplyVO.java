package com.example.test.sns;

import java.io.Serializable;
import java.util.Date;

public class SnsReplyVO implements Serializable {
    int re_no, sns_no;
    String title, reply, id;
    Date re_date;

    public int getRe_no() {
        return re_no;
    }

    public void setRe_no(int re_no) {
        this.re_no = re_no;
    }

    public int getSns_no() {
        return sns_no;
    }

    public void setSns_no(int sns_no) {
        this.sns_no = sns_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getRe_date() {
        return re_date;
    }

    public void setRe_date(Date re_date) {
        this.re_date = re_date;
    }
}
