package com.example.igor.translator.ui.AddWord;

import com.example.igor.translator.api.YandexAPIService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by igor on 21.08.16.
 *
 */

@Module
public class AddWordPresenterModule {
    @Provides
    @Singleton
    AddWordContract.Presenter provideAddWordPresenter(YandexAPIService apiService){
        return new AddWordPresenter(apiService);
    }
}
