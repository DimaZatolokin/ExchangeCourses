package com.example.dimazatolokin.myapplication.net;

import android.text.TextUtils;
import android.util.Log;

import com.example.dimazatolokin.myapplication.data.model.CurrencyCode;
import com.example.dimazatolokin.myapplication.data.model.OperationDb;
import com.example.dimazatolokin.myapplication.data.sqlite.DaoSession;
import com.example.dimazatolokin.myapplication.net.callback.CoursesResponseCallback;
import com.example.dimazatolokin.myapplication.net.callback.CurrencyCodesCallback;
import com.example.dimazatolokin.myapplication.net.response.CourseResponse;
import com.example.dimazatolokin.myapplication.net.response.CurrencyCodesResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String TAG = NetworkService.class.getSimpleName();
    private final ApiService apiService;

    public NetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void obtainCurrencyCodes(final CurrencyCodesCallback callback) {
        Call<CurrencyCodesResponse> call = apiService.getCurrencyCodes();
        call.enqueue(new Callback<CurrencyCodesResponse>() {
            @Override
            public void onResponse(Call<CurrencyCodesResponse> call, Response<CurrencyCodesResponse> response) {
                if (response.isSuccessful()) {
                    List<CurrencyCode> codeList = response.body().getData();
                    callback.onSuccess(codeList);
                    Log.i(TAG, "onResponse: ");
                } else {
                    try {
                        callback.onError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "onResponse error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CurrencyCodesResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t.fillInStackTrace());
                callback.onError(t.getMessage());
            }
        });
    }

    public void getCourse(final DaoSession daoSession, String firstCode, String secondCode, final CoursesResponseCallback callback) {
        Call<CourseResponse> call = apiService.getCourse(firstCode, secondCode);
        call.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                if (response.isSuccessful()) {
                    if (!TextUtils.isEmpty(response.body().getError())) {
                        callback.onError(response.body().getError());
                    } else {
                        Log.i(TAG, "onResponse: ");
                        callback.onSuccess(response.body().getTicker());
                        daoSession.getOperationBookDao().save(new OperationDb(response.body().getTicker(), response.body().getTimestamp()));
                    }
                } else {
                    Log.i(TAG, "onResponse error: " + response.errorBody());
                    try {
                        callback.onError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t.fillInStackTrace());
                callback.onError(t.getMessage());
            }
        });
    }
}
