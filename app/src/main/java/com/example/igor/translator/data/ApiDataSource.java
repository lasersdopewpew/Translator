package com.example.igor.translator.data;

import com.example.igor.translator.data.WordEntry;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by igor on 22.08.16.
 */

public interface ApiDataSource {
    Observable<WordEntry> searchString(String search, String fromLangCode, String toLangCode);
    Observable<ArrayList<String>> loadLangs();
}
