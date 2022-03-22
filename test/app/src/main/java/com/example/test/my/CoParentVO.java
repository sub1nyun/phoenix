package com.example.test.my;

import java.io.Serializable;

public class CoParentVO implements Serializable {
    private String title, id, family_rels;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFamily_rels() {
        return family_rels;
    }

    public void setFamily_rels(String family_rels) {
        this.family_rels = family_rels;
    }
}