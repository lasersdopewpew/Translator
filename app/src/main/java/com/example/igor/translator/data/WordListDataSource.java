package com.example.igor.translator.data;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by igor on 20.08.16.
 *
 */

public interface WordListDataSource {
    void addWord(WordEntry wordEntry);
    Observable<List<WordEntry>> getWords();
    Observable<List<WordEntry>> searchWords(String request);
    void deleteWord(WordEntry wordEntry);
}
