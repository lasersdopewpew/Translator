package com.example.igor.translator.api.YandexApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by igor on 20.08.16.
 *
 */

public class Meaning {
    @SerializedName("text")
    @Expose
    private String text;
}
