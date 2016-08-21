package com.example.igor.translator.ui.AddWord;

import android.view.View;

import com.example.igor.translator.data.WordEntry;

import java.util.ArrayList;

/**
 * Created by igor on 21.08.16.
 */

public interface AddWordContract {

    interface View {

        void setReturnWordEntry(WordEntry returnWordEntry);

        void setTranslatedWord(String translatedWord);

        void clearErrorAndTranslation();

        void setWordNotFound();

        void addWordButtonSetEnabled(boolean enabled);

        void pbSearchSetEnabled(boolean enabled);

        void setFromLanguages(ArrayList<Lang> fromLang);

        void setToLanguages(ArrayList<Lang> toLanguages);

        void showNetworkError();
    }

    interface Presenter {

        void setView(AddWordActivity addWordActivity);

        void fromLangSelected(String code);

        void searchString(String s, String code, String code1);
    }
}
