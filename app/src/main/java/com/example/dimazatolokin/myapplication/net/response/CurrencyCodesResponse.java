package com.example.dimazatolokin.myapplication.net.response;

import com.example.dimazatolokin.myapplication.data.model.CurrencyCode;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrencyCodesResponse {

    @SerializedName("rows")
    private List<CurrencyCode> data;

    public List<CurrencyCode> getData() {
        return data;
    }

    public void setData(List<CurrencyCode> data) {
        this.data = data;
    }
}
