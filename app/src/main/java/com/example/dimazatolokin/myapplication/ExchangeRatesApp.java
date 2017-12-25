package com.example.dimazatolokin.myapplication;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.example.dimazatolokin.myapplication.data.model.CurrencyCode;
import com.example.dimazatolokin.myapplication.data.sqlite.DaoSession;
import com.example.dimazatolokin.myapplication.net.NetworkService;
import com.example.dimazatolokin.myapplication.net.callback.CurrencyCodesCallback;
import com.example.dimazatolokin.myapplication.presenter.PresenterManager;
import com.example.dimazatolokin.myapplication.view.MainActivity;

import java.util.List;

public class ExchangeRatesApp extends Application {

    private List<CurrencyCode> currencyCodes;
    private DaoSession daoSession;
    private NetworkService networkService;
    private PresenterManager presenterManager;

    @Override
    public void onCreate() {
        super.onCreate();

        networkService = new NetworkService();
        daoSession = new DaoSession(getApplicationContext());
        networkService.obtainCurrencyCodes(new CurrencyCodesCallbackImpl());
        presenterManager = new PresenterManager(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private void fillCurrencyCodes(List<CurrencyCode> codes) {
        currencyCodes = codes;
    }

    public List<CurrencyCode> getCurrencyCodes() {
        return currencyCodes;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    public PresenterManager getPresenterManager() {
        return presenterManager;
    }

    private class CurrencyCodesCallbackImpl implements CurrencyCodesCallback {

        @Override
        public void onSuccess(List<CurrencyCode> codeList) {
            fillCurrencyCodes(codeList);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        @Override
        public void onError(String message) {
            Toast.makeText(getApplicationContext(), getString(R.string.wrong_response_toast), Toast.LENGTH_LONG).show();
        }
    }
}
