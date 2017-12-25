package com.example.dimazatolokin.myapplication.net;

import com.example.dimazatolokin.myapplication.net.response.CourseResponse;
import com.example.dimazatolokin.myapplication.net.response.CurrencyCodesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    String BASE_URL = "https://api.cryptonator.com/api/";

    @GET("currencies")
    Call<CurrencyCodesResponse> getCurrencyCodes();

    @GET("ticker/{code1}-{code2}")
    Call<CourseResponse> getCourse(@Path("code1") String code1, @Path("code2") String code2);
}
