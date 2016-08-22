package com.example.igor.translator.ui.List;

import com.example.igor.translator.data.WordEntry;

import java.util.List;

/**
 * Created by igor on 21.08.16.
 *
 */

public interface WordListContract {

    interface View {

        void addWord(WordEntry wordEntry);

        void setWorldList(List<WordEntry> wordEntries);

        void removeItem(WordEntry adapterPosition);

        void showError();

        void showEmptyListPlaceHolder();
    }

    interface Presenter {

        void setView(WordListContract.View view);

        void detachView();

        void start();

        void addWordEntry(WordEntry wordEntry);

        void removeWordEntry(WordEntry wordEntry);
    }
}
