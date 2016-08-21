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
package com.example.igor.translator.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.igor.translator.data.WordEntry;

final class DbOpenHelper extends SQLiteOpenHelper {
  private static final int VERSION = 1;

  private static final String CREATE_ITEM = ""
          + "CREATE TABLE " + WordEntry.TABLE + "("
          + WordEntry.ID + " INTEGER NOT NULL PRIMARY KEY,"
          + WordEntry.ORIGINAL_WORD + " TEXT NOT NULL,"
          + WordEntry.TRANSLATED_WORD + " TEXT NOT NULL,"
          + WordEntry.TRANSCRIPTION + " TEXT,"
          + WordEntry.PART_OF_SPEECH + " TEXT"
          + ")";

  public DbOpenHelper(Context context) {
        super(context, "wordlist1234567.db", null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITEM);

        db.insert(WordEntry.TABLE, null, new WordEntry.Builder()
            .wordOriginal("Original")
            .wordTranslation("Оригинал")
            .wordTranscription("Origina")
            .wordPartOfSpeech("asdd")
            .build());
        db.insert(WordEntry.TABLE, null, new WordEntry.Builder()
              .wordOriginal("Original 2")
              .wordTranslation("Оригинал 2")
              .wordTranscription("Origina 2")
              .wordPartOfSpeech("asddd")
              .build());
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}