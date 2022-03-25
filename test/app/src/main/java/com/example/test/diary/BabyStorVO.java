package com.example.test.diary;

import java.sql.Date;

public class BabyStorVO {
	String baby_id;
	int stor_id;
	double stor_kg, stor_cm;
	String stor_date;
	public String getBaby_id() {
		return baby_id;
	}
	public void setBaby_id(String baby_id) {
		this.baby_id = baby_id;
	}
	public int getStor_id() {
		return stor_id;
	}
	public void setStor_id(int stor_id) {
		this.stor_id = stor_id;
	}
	public double getStor_kg() {
		return stor_kg;
	}
	public void setStor_kg(double stor_kg) {
		this.stor_kg = stor_kg;
	}
	public double getStor_cm() {
		return stor_cm;
	}
	public void setStor_cm(double stor_cm) {
		this.stor_cm = stor_cm;
	}
	public String getStor_date() {
		return stor_date;
	}
	public void setStor_date(String stor_date) {
		this.stor_date = stor_date;
	}
}