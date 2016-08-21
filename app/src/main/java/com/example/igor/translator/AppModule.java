package com.example.igor.translator;

import android.app.Application;

import com.example.igor.translator.api.YandexAPIService;
import com.example.igor.translator.data.WordListDataSource;
import com.example.igor.translator.ui.AddWord.AddWordContract;
import com.example.igor.translator.ui.AddWord.AddWordPresenter;
import com.example.igor.translator.ui.List.WordListContract;
import com.example.igor.translator.ui.List.WordListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by igor on 20.08.16.
 *
 */

@Module
public class AppModule {
    private TranslatorApp app;

    public AppModule(TranslatorApp app) {
    this.app = app;
  }

    @Provides
    @Singleton
    Application provideApplication() {
        return this.app;
    }
}
