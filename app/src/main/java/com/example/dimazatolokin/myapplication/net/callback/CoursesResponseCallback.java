package com.example.dimazatolokin.myapplication.net.callback;

import com.example.dimazatolokin.myapplication.data.model.Ticker;

public interface CoursesResponseCallback {

    void onSuccess(Ticker ticker);
    void onError(String message);
}
