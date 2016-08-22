package com.example.igor.translator.data.api.YandexApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by igor on 20.08.16.
 *
 */

public class YandexResponse {
    @SerializedName("def")
    @Expose
    public ArrayList<Definition> definition;
}

