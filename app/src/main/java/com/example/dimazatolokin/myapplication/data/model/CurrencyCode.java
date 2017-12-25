package com.example.dimazatolokin.myapplication.data.model;

import java.util.List;

public class CurrencyCode {

    private String code;
    private String name;
    private List<String> statuses;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }
}
