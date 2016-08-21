/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.igor.translator.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.igor.translator.data.local.DbUtil;
import com.google.auto.value.AutoValue;

import rx.functions.Func1;

@AutoValue
public abstract class WordEntry implements Parcelable {
    public static final String TABLE = "word_list";

    public static final String ID = "_id";
    public static final String ORIGINAL_WORD = "original_word";
    public static final String TRANSLATED_WORD = "description";
    public static final String TRANSCRIPTION = "transcription";
    public static final String PART_OF_SPEECH = "part_of_speech";

    public abstract int id();
    public abstract String wordOriginal();
    public abstract String wordTranslation();
    @Nullable
    public abstract String wordTranscription();
    @Nullable
    public abstract String partOfSpeech();

    public static final Func1<Cursor, WordEntry> MAPPER = new Func1<Cursor, WordEntry>() {
        @Override
        public WordEntry call(Cursor cursor) {
            int id = DbUtil.getInt(cursor, ID);
            String wordOriginal = DbUtil.getString(cursor, ORIGINAL_WORD);
            String wordTranslation = DbUtil.getString(cursor, TRANSLATED_WORD);
            String wordTranscription = DbUtil.getString(cursor, TRANSCRIPTION);
            String partOfSpeech = DbUtil.getString(cursor, PART_OF_SPEECH);
            return new AutoValue_WordEntry(id, wordOriginal, wordTranslation, wordTranscription, partOfSpeech);
        }
    };

    public static WordEntry create(String wordOriginal, String wordTranslation, String wordTranscription, String partOfSpeech) {
        return new AutoValue_WordEntry(0, wordOriginal, wordTranslation, wordTranscription, partOfSpeech);
    }

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder wordOriginal(String wordOriginal) {
          values.put(ORIGINAL_WORD, wordOriginal);
          return this;
        }

        public Builder wordTranslation(String wordTranslation) {
          values.put(TRANSLATED_WORD, wordTranslation);
          return this;
        }

        public Builder wordTranscription(String transcription) {
          values.put(TRANSCRIPTION, transcription);
          return this;
        }

        public Builder wordPartOfSpeech(String pos) {
            values.put(PART_OF_SPEECH, pos);
            return this;
        }

        public ContentValues build() {
          return values;
        }
    }
}
