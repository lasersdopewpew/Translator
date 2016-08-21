package com.example.igor.translator.ui.AddWord;

/**
 * Created by igor on 21.08.16.
 */

public class Lang {
    String code;
    String localizedName;

    public Lang(String code, String localizedName) {
        this.code = code;
        this.localizedName = localizedName;
    }

    @Override
    public String toString() {
        return localizedName;
    }
}
