package com.example.igor.translator;

import android.app.Application;
import android.content.Context;

/**
 * Created by igor on 20.08.16.
 *
 */

public class TranslatorApp extends Application {
  private AppComponent component;

  protected AppModule getApplicationModule() {
    return new AppModule(this);
  }

  public static AppComponent getAppComponent(Context context) {
    TranslatorApp app = (TranslatorApp) context.getApplicationContext();
    if (app.component == null) {
      app.component = DaggerAppComponent.builder()
          .appModule(app.getApplicationModule())
          .build();
    }
    return app.component;
  }

  public static void clearAppComponent(Context context) {
    TranslatorApp app = (TranslatorApp) context.getApplicationContext();
    app.component = null;
  }
}
