package com.example.igor.translator.data;

import com.example.igor.translator.data.api.YandexApiResponse.Definition;
import com.example.igor.translator.data.api.YandexApiResponse.YandexResponse;
import com.example.igor.translator.data.api.YandexApiService;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by igor on 22.08.16.
 *
 */

public class YandexApiDataSource implements ApiDataSource {

    YandexApiService yandexApiService;

    @Inject
    YandexApiDataSource(YandexApiService yandexApiService){
        this.yandexApiService = yandexApiService;
    }

    @Override
    public Observable<WordEntry> searchString(String search, String fromLangCode, String toLangCode) {
        Observable<YandexResponse> call = yandexApiService.lookup(search, fromLangCode, toLangCode);

        return call.map(this::responseToWordEntry);
    }

    private WordEntry responseToWordEntry(YandexResponse response) {

        if (response.definition.isEmpty() ||
                response.definition.get(0).getTranslation().isEmpty() ||
                response.definition.get(0).getTranslation().get(0).text == null ||
                response.definition.get(0).getTranslation().get(0).text.isEmpty()) {
            return WordEntry.create(0, "", "", "", "");
        }

        Definition firstDefinition = response.definition.get(0);

        return WordEntry.create(firstDefinition.text,
                firstDefinition.translation.get(0).text,
                firstDefinition.getTs(),
                firstDefinition.getPos());
    }

    @Override
    public Observable<ArrayList<String>> loadLangs() {
        return yandexApiService.getLangs();
    }
}
