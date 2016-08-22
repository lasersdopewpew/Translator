package com.example.igor.translator;

import android.app.Application;

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
