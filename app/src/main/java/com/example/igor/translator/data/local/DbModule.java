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

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.igor.translator.data.WordListDataSource;
import com.example.igor.translator.ui.List.WordListContract;
import com.example.igor.translator.ui.List.WordListPresenter;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
public final class DbModule {
    @Provides
    @Singleton SQLiteOpenHelper provideOpenHelper(Application application) {
        return new DbOpenHelper(application);
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return SqlBrite.create(message -> Log.i("SqlBrite", message));
    }

    @Provides
    @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        return sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
    }

    @Provides
    @Singleton
    WordListDataSource provideDataSource(BriteDatabase briteDatabase) {
        return new LocalDBDataSource(briteDatabase);
    }
}
