package com.example.igor.translator.api;

import com.example.igor.translator.api.YandexApiResponse.YandexResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by igor on 20.08.16.
 */

public class YandexAPIService {
    private YandexTranslatorAPI yandexAPIService;

    private static final int FAMILY = 0x0001; // family filter
    private static final int SHORT_POS = 0x0002; // short forms of parts of speech
    private static final int MORPHO = 0x0004; // morpho search
    private static final int POS_FILTER = 0x0008; // original and translation POS must be equal

    private static final int ERR_OK	= 200;
    private static final int ERR_KEY_INVALID = 401;
    private static final int ERR_KEY_BLOCKED = 402;
    private static final int ERR_DAILY_REQ_LIMIT_EXCEEDED = 403;
    private static final int ERR_TEXT_TOO_LONG = 413;
    private static final int ERR_LANG_NOT_SUPPORTED = 501;

    private static final String BASE_URL = "https://dictionary.yandex.net/";
    private static final String API_KEY = "dict.1.1.20160820T000034Z.d3b63c5a40a5cddd.8e78d6af335bf2fa2d41708960abd5fb49ac6ec5";

    YandexAPIService(){
        Gson gson = new GsonBuilder().create();



        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        yandexAPIService = retrofit.create(YandexTranslatorAPI.class);
    }

    public Observable<YandexResponse> lookup(String text, String from, String to) {
        return yandexAPIService.lookup(API_KEY, from+"-"+to, text, POS_FILTER);
    }

    public Observable<ArrayList<String>> getLangs() {
        return yandexAPIService.getLangs(API_KEY);
    }
}
