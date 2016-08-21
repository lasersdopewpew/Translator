package com.example.igor.translator.ui.List;

import android.util.Log;

import com.example.igor.translator.data.WordListDataSource;
import com.example.igor.translator.data.WordEntry;

import javax.inject.Inject;

/**
 * Created by igor on 20.08.16.
 */

public class WordListPresenter implements WordListContract.Presenter {

    WordListContract.View view;
    private WordListDataSource db;

    @Inject
    public WordListPresenter(WordListDataSource wordListDataSource) {

        this.db = wordListDataSource;

    }

    @Override
    public void setView(WordListContract.View view) {
        this.view = view;

        db.getWords()
                .subscribe(view::setWorldList,
                           this::processError
                );
    }

    private void processError(Throwable error) {
        Log.e("Error!", error.getLocalizedMessage());
    }

    @Override
    public void addWordEntry(WordEntry wordEntry) {
        db.addWord(wordEntry);

        view.addWord(wordEntry);
    }

    @Override
    public void removeWordEntry(WordEntry wordEntry) {
        db.deleteWord(wordEntry);
    }
}
