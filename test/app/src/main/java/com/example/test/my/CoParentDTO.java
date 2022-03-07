package com.example.test.my;

public class CoParentDTO {
    private String co_name, co_rels;

    public CoParentDTO(String co_name, String co_rels) {
        this.co_name = co_name;
        this.co_rels = co_rels;
    }

    public String getCo_name() {
        return co_name;
    }

    public void setCo_name(String co_name) {
        this.co_name = co_name;
    }

    public String getCo_rels() {
        return co_rels;
    }

    public void setCo_rels(String co_rels) {
        this.co_rels = co_rels;
    }
}