package com.example.dimazatolokin.myapplication.presenter;

import com.example.dimazatolokin.myapplication.ExchangeRatesApp;
import com.example.dimazatolokin.myapplication.R;
import com.example.dimazatolokin.myapplication.data.model.CurrencyCode;
import com.example.dimazatolokin.myapplication.data.model.OperationDb;
import com.example.dimazatolokin.myapplication.data.model.Ticker;
import com.example.dimazatolokin.myapplication.net.callback.CoursesResponseCallback;
import com.example.dimazatolokin.myapplication.view.BaseView;

import java.util.ArrayList;
import java.util.List;

public class Presenter implements BasePresenter {

    private BaseView view;
    private String viewTag;

    private int firstSpinnerPosition = 0;
    private int secondSpinnerPosition = 0;
    private ExchangeRatesApp app;

    public Presenter(ExchangeRatesApp app) {
        this.app = app;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = view;
        viewTag = view.getClass().getSimpleName();
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public List<CharSequence> getCurrencyNames() {
        List<CharSequence> codes = new ArrayList<>();
        for (CurrencyCode currencyCode : app.getCurrencyCodes()) {
            codes.add(currencyCode.getName());
        }
        return codes;
    }

    @Override
    public BaseView getView() {
        return view;
    }

    @Override
    public String getTag() {
        return viewTag;
    }

    @Override
    public int getFirstSpinnerPosition() {
        return firstSpinnerPosition;
    }

    @Override
    public void setFirstSpinnerPosition(int firstSpinnerPosition) {
        this.firstSpinnerPosition = firstSpinnerPosition;
    }

    @Override
    public int getSecondSpinnerPosition() {
        return secondSpinnerPosition;
    }

    @Override
    public void setSecondSpinnerPosition(int secondSpinnerPosition) {
        this.secondSpinnerPosition = secondSpinnerPosition;
    }

    @Override
    public void obtainCourses() {
        if (firstSpinnerPosition == secondSpinnerPosition) {
            return;
        }
        view.showProgress(app.getString(R.string.wait));
        app.getNetworkService().getCourse(app.getDaoSession(),
                app.getCurrencyCodes().get(firstSpinnerPosition).getCode(),
                app.getCurrencyCodes().get(secondSpinnerPosition).getCode(), new ObtainCoursesCallback());
    }

    @Override
    public List<OperationDb> getHistory() {
        List<OperationDb> allOperations = app.getDaoSession().getOperationBookDao().getAllOperations();
        return allOperations;
    }

    private class ObtainCoursesCallback implements CoursesResponseCallback {

        @Override
        public void onSuccess(Ticker ticker) {
            view.hideProgress();
            view.setCourses(ticker.getPrice());
        }

        @Override
        public void onError(String message) {
            view.hideProgress();
            view.showMessage(message);
        }
    }
}
