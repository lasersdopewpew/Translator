package com.example.igor.translator.data.local;

import com.example.igor.translator.data.WordEntry;
import com.example.igor.translator.data.WordListDataSource;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by igor on 21.08.16.
 *
 */

public class LocalDBDataSource implements WordListDataSource {

    private BriteDatabase db;

    private static String QUERY = ""
            + "SELECT " + WordEntry.ID + ","
            + WordEntry.ORIGINAL_WORD + ","
            + WordEntry.TRANSLATED_WORD + ","
            + WordEntry.TRANSCRIPTION + ","
            + WordEntry.PART_OF_SPEECH
            + " FROM " + WordEntry.TABLE;

    @Inject
    public LocalDBDataSource(BriteDatabase briteDatabase){
        this.db = briteDatabase;
    }

    @Override
    public void addWord(WordEntry wordEntry) {
        db.insert(WordEntry.TABLE, new WordEntry.Builder()
                .wordOriginal(wordEntry.wordOriginal())
                .wordTranslation(wordEntry.wordTranslation())
                .wordTranscription(wordEntry.wordTranscription())
                .wordPartOfSpeech(wordEntry.partOfSpeech())
                .build());
    }

    @Override
    public Observable<List<WordEntry>> getWords() {
        return db.createQuery(WordEntry.TABLE, QUERY)
                .mapToList(WordEntry.MAPPER)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<WordEntry>> searchWords(String request) {
        return null;
    }

    @Override
    public void deleteWord(WordEntry wordEntry) {
        db.delete(WordEntry.TABLE, WordEntry.ID + " = " + wordEntry.id());
    }
}
