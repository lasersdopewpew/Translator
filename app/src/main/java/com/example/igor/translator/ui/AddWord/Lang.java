package com.example.igor.translator.ui.AddWord;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by igor on 21.08.16.
 *
 */

@AutoValue
abstract class Lang implements Parcelable {
    abstract String code();
    abstract String localizedName();

    static Lang create(String code, String localizedName) {
        return new AutoValue_Lang(code, localizedName);
    }

    @Override
    public String toString() {
        return localizedName();
    }
}
