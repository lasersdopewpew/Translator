package com.example.igor.translator;

import com.example.igor.translator.data.api.YandexApiServiceModule;
import com.example.igor.translator.data.DataModule;
import com.example.igor.translator.ui.AddWord.AddWordActivity;
import com.example.igor.translator.ui.AddWord.AddWordPresenterModule;
import com.example.igor.translator.ui.List.WordListActivity;
import com.example.igor.translator.ui.List.WordListPresenterModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by igor on 20.08.16.
 *
 */

@Component(
    modules = { AppModule.class, YandexApiServiceModule.class, DataModule.class,
            WordListPresenterModule.class, AddWordPresenterModule.class}
)
@Singleton
public interface AppComponent {
    WordListActivity inject(WordListActivity activity);
    AddWordActivity inject(AddWordActivity activity);
}
