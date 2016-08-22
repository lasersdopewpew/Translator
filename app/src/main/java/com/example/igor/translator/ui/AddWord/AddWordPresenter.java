package com.example.igor.translator.ui.AddWord;

import android.util.Pair;

import com.example.igor.translator.data.ApiDataSource;
import com.example.igor.translator.data.WordEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by igor on 21.08.16.
 *
 */

public class AddWordPresenter implements AddWordContract.Presenter {

    private ApiDataSource apiDataSource;
    private AddWordActivity view;

    private HashMap<String, ArrayList<Lang>> translDirections;

    private CompositeSubscription subscriptions;

    public AddWordPresenter(ApiDataSource apiDataSource){
        this.apiDataSource = apiDataSource;
    }

    @Override
    public void attachView(AddWordActivity view) {
        this.view = view;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        this.view = null;
        subscriptions.unsubscribe();
    }

    @Override
    public void start() {
        loadLangs();
    }

    @Override
    public void searchString(String search, String fromLangCode, String toLangCode){
        view.addWordButtonSetEnabled(false);
        view.pbSearchSetEnabled(true);

        Observable<WordEntry> call = apiDataSource.searchString(search, fromLangCode, toLangCode);

        subscriptions.add(
            call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(this::checkEmptyResult)
                .subscribe(
                        this::processSearchResponse,
                        this::processError));
    }

    private void loadLangs() {
        Observable<ArrayList<String>> call = apiDataSource.loadLangs();

        subscriptions.add(
                call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::processGetLangs,
                        this::processError));
    }

    private void processGetLangs(ArrayList<String> strings) {
        parseLangs(strings);
    }

    @Override
    public void fromLangSelected(String code) {
        view.setToLanguages(translDirections.get(code));
    }

    private void parseLangs(ArrayList<String> langs){
        translDirections = new HashMap<>();
        ArrayList<Pair<String, String>> langPairs = new ArrayList<>();
        for (String p: langs){
            String[] pair = p.split("-");
            langPairs.add(Pair.create(pair[0], pair[1]));
        }

        ArrayList<Lang> fromLanguages = new ArrayList<>();

        for (Pair<String, String> h: langPairs){
            String fromLang = h.first;
            String toLang = h.second;

            if (fromLang.equals(toLang)) continue;

            if (translDirections.containsKey(fromLang)) {
                translDirections.get(fromLang).add(Lang.create(toLang, longName(toLang)));
            } else {
                translDirections.put(fromLang,
                        new ArrayList<>(
                                Collections.singletonList(Lang.create(toLang, longName(toLang)))));
                fromLanguages.add(Lang.create(fromLang, longName(fromLang)));
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
    }

    private void processSearchResponse(WordEntry wordEntry) {
        view.pbSearchSetEnabled(false);
        view.setReturnWordEntry(wordEntry);
        view.setTranslatedWord(wordEntry.wordTranslation());
        view.addWordButtonSetEnabled(true);
    }

    private boolean checkEmptyResult(WordEntry wordEntry) {
        if (wordEntry.wordOriginal().isEmpty()) {
            view.setWordNotFound();
            view.pbSearchSetEnabled(false);
            return false;
        }
        return true;
    }
}
