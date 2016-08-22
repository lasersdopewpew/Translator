package com.example.igor.translator.data.api;

import com.example.igor.translator.data.api.YandexApiResponse.YandexResponse;

import java.util.ArrayList;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by igor on 20.08.16.
 *
 */

interface YandexTranslatorAPI {
    @POST("api/v1/dicservice.json/lookup")
    Observable<YandexResponse> lookup(@Query("key") String key,
                                      @Query("lang") String from,
                                      @Query("text") String text,
                                      @Query("flags") int flags);

    @POST("api/v1/dicservice.json/getLangs")
    Observable<ArrayList<String>> getLangs(@Query("key") String key);
}
