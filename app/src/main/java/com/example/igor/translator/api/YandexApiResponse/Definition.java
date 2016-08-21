package com.example.igor.translator.api.YandexApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Definition {
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("pos")
    @Expose
    private String pos;
    @SerializedName("ts")
    @Expose
    private String ts;
    @SerializedName("tr")
    @Expose
    public List<Translation> translation = new ArrayList<Translation>();

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }

    public String getTs() {
        return ts;
    }

    public List<Translation> getTranslation() {
        return translation;
    }
}
