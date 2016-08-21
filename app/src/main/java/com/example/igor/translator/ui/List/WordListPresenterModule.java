package com.example.igor.translator.ui.List;

import com.example.igor.translator.data.WordListDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by igor on 21.08.16.
 *
 */

@Module
public class WordListPresenterModule {
    @Singleton
    @Provides
    WordListContract.Presenter provideWordListPresenter(WordListDataSource wordListDataSource) {
        return new WordListPresenter(wordListDataSource);
    }
}
