package com.example.igor.translator.api.YandexApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 20.08.16.
 *
 */

public class Example {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("tr")
    @Expose
    private List<Translation> tr = new ArrayList<Translation>();
}
