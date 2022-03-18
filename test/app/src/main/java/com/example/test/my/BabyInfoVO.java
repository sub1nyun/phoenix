package com.example.test.my;

import java.io.Serializable;
import java.sql.Date;

public class BabyInfoVO implements Serializable {
	String baby_id, title, baby_name, baby_photo, baby_gender;
	double baby_kg, baby_cm;
	Date baby_birth;

	public String getBaby_id() {
		return baby_id;
	}

	public void setBaby_id(String baby_id) {
		this.baby_id = baby_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBaby_name() {
		return baby_name;
	}

	public void setBaby_name(String baby_name) {
		this.baby_name = baby_name;
	}

	public String getBaby_photo() {
		return baby_photo;
	}

	public void setBaby_photo(String baby_photo) {
		this.baby_photo = baby_photo;
	}

	public String getBaby_gender() {
		return baby_gender;
	}

	public void setBaby_gender(String baby_gender) {
		this.baby_gender = baby_gender;
	}

	public double getBaby_kg() {
		return baby_kg;
	}

	public void setBaby_kg(double baby_kg) {
		this.baby_kg = baby_kg;
	}

	public double getBaby_cm() {
		return baby_cm;
	}

	public void setBaby_cm(double baby_cm) {
		this.baby_cm = baby_cm;
	}

	public Date getBaby_birth() {
		return baby_birth;
	}

	public void setBaby_birth(Date baby_birth) {
		this.baby_birth = baby_birth;
	}
}