package com.example.dimazatolokin.myapplication.presenter;

import com.example.dimazatolokin.myapplication.ExchangeRatesApp;
import com.example.dimazatolokin.myapplication.view.BaseView;

import java.util.ArrayList;
import java.util.List;

public class PresenterManager {

    private List<BasePresenter> presenters = new ArrayList<>();
    private ExchangeRatesApp app;

    public PresenterManager(ExchangeRatesApp app) {
        this.app = app;
    }
   /* public BasePresenter getPresenter(String viewTag) {
        for (BasePresenter presenter : presenters) {
            if (presenter.getView() != null && presenter.getView().getTag().equals(viewTag)) {
                return presenter;
            }
        }
        return new Presenter();
    }*/

  /*  public void registerPresenter(BasePresenter presenter) {
        if (presenter == null) {
            return;
        }
        for (BasePresenter basePresenter : presenters) {
            if (basePresenter.getView().equals(presenter.getView())) {
                presenter = basePresenter;
                return;
            }
        }
        presenters.add(presenter);
    }*/

    public BasePresenter getPresenter(BaseView baseView) {
        for (BasePresenter presenter : presenters) {
            if (presenter.getTag().equals(baseView.getClass().getSimpleName())) {
                presenter.attachView(baseView);
                return presenter;
            }
        }
        Presenter presenter = new Presenter(app);
        presenter.attachView(baseView);
        presenters.add(presenter);
        return presenter;
    }
}