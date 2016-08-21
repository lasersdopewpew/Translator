package com.example.igor.translator.api;

import dagger.Module;
import dagger.Provides;

/**
 * Created by igor on 20.08.16.
 *
 */

@Module
public class YandexApiServiceModule {
    @Provides
    YandexAPIService provideYandexAPIService(){
        return new YandexAPIService();
    }
}
