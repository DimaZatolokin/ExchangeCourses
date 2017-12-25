package com.example.dimazatolokin.myapplication.presenter;

import com.example.dimazatolokin.myapplication.data.model.OperationDb;
import com.example.dimazatolokin.myapplication.view.BaseView;

import java.util.List;

public interface BasePresenter {

    void attachView(BaseView view);

    void detachView();

    List<CharSequence> getCurrencyNames();

    BaseView getView();

    String getTag();

    int getFirstSpinnerPosition();

    void setFirstSpinnerPosition(int firstSpinnerPosition);

    int getSecondSpinnerPosition();

    void setSecondSpinnerPosition(int secondSpinnerPosition);

    void obtainCourses();

    List<OperationDb> getHistory();
}
