package com.example.test.my;

import java.io.Serializable;

public class BabyInfoVO implements Serializable {
	String baby_id, title, baby_name, baby_photo, baby_gender, id, body;
	String baby_birth;

	public String getId() { return id; }

	public void setId(String id) { this.id = id; }

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

	public String getBaby_birth() {
		return baby_birth;
	}

	public void setBaby_birth(String baby_birth) {
		this.baby_birth = baby_birth;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}