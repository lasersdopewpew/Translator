package com.example.igor.translator.ui.AddWord;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Pair;

import com.example.igor.translator.api.YandexAPIService;
import com.example.igor.translator.api.YandexApiResponse.Definition;
import com.example.igor.translator.api.YandexApiResponse.YandexResponse;
import com.example.igor.translator.data.WordEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by igor on 21.08.16.
 *
 */

public class AddWordPresenter implements AddWordContract.Presenter {

    private YandexAPIService yandexAPIService;
    private AddWordActivity view;

    private HashMap<String, ArrayList<Lang>> translDirections;

    public AddWordPresenter(YandexAPIService yandexAPIService){
        this.yandexAPIService = yandexAPIService;
    }

    @Override
    public void setView(AddWordActivity view) {
        this.view = view;
        loadLangs();
    }

    @Override
    public void searchString(String search, String fromLangCode, String toLangCode){
        view.addWordButtonSetEnabled(false);
        view.pbSearchSetEnabled(true);

        Observable<YandexResponse> call = yandexAPIService.lookup(search, fromLangCode, toLangCode);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(this::checkEmptyResult)
                .subscribe(
                        this::processSearchResponse,
                        this::processError);
    }

    private void processGetLangs(ArrayList<String> strings) {
        parseLangs(strings);
    }

    private void loadLangs() {
        Observable<ArrayList<String>> call = yandexAPIService.getLangs();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::processGetLangs,
                        this::processError);
    }

    @Override
    public void fromLangSelected(String code) {
        view.setToLanguages(translDirections.get(code));
    }

    private void parseLangs(ArrayList<String> langs){
        translDirections = new HashMap<>();
        ArrayList<Pair<String, String>> hashMap = new ArrayList<>();
        for (String p: langs){
            String[] pair = p.split("-");
            hashMap.add(Pair.create(pair[0], pair[1]));
        }

        ArrayList<Lang> fromLanguages = new ArrayList<>();

        for (Pair<String, String> h: hashMap){
            String fromLang = h.first;
            String toLang = h.second;

            if (fromLang.equals(toLang)) continue;

            if (translDirections.containsKey(fromLang)) {
                translDirections.get(fromLang).add(new Lang(toLang, longName(toLang)));
            } else {
                translDirections.put(fromLang,
                        new ArrayList<>(
                                Collections.singletonList(new Lang(toLang, longName(toLang)))));
                fromLanguages.add(new Lang(fromLang, longName(fromLang)));
            }
        }

        view.setFromLanguages(fromLanguages);
    }

    private String longName(String code){
        Locale loc = new Locale(code);
        return loc.getDisplayLanguage(loc);
    }


    private void processError(Throwable error) {
        view.pbSearchSetEnabled(false);
        view.showNetworkError();
        Log.e("ERROR", error.getMessage());
    }

    private void processSearchResponse(YandexResponse yandexResponse) {
        view.pbSearchSetEnabled(false);
        Definition firstDefinition = yandexResponse.definition.get(0);

        view.setReturnWordEntry(WordEntry.create(firstDefinition.text,
                firstDefinition.translation.get(0).text,
                firstDefinition.getTs(),
                firstDefinition.getPos()));
        view.setTranslatedWord(firstDefinition.translation.get(0).text);
        view.addWordButtonSetEnabled(true);
    }

    private boolean checkEmptyResult(YandexResponse r) {
        if (r.definition.isEmpty() ||
                r.definition.get(0).getTranslation().isEmpty() ||
                r.definition.get(0).getTranslation().get(0).text == null ||
                r.definition.get(0).getTranslation().get(0).text.isEmpty()) {
            view.setWordNotFound();
            view.pbSearchSetEnabled(false);
            return false;
        }
        return true;
    }

}
