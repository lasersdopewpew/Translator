package com.example.igor.translator.ui.List;

import android.util.Log;

import com.example.igor.translator.data.WordListDataSource;
import com.example.igor.translator.data.WordEntry;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by igor on 20.08.16.
 *
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
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void start() {
        db.getWords()
                .subscribe(this::processResult,
                           this::processError
                );
    }

    private void processResult(List<WordEntry> wordEntries) {
        view.setWorldList(wordEntries);
        if (wordEntries.size() == 0) view.showEmptyListPlaceHolder();
    }

    private void processError(Throwable error) {
        view.showError();
    }

    @Override
    public void addWordEntry(WordEntry wordEntry) {
        db.addWord(wordEntry);
    }

    @Override
    public void removeWordEntry(WordEntry wordEntry) {
        db.deleteWord(wordEntry);
    }
}
