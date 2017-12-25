package com.example.dimazatolokin.myapplication.net.callback;


import com.example.dimazatolokin.myapplication.data.model.CurrencyCode;

import java.util.List;

public interface CurrencyCodesCallback {

    void onSuccess(List<CurrencyCode> codeList);

    void onError(String message);
}
