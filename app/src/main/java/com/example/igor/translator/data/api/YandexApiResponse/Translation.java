package com.example.igor.translator.data.api.YandexApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Translation {
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("pos")
    @Expose
    private String pos;
    @SerializedName("gen")
    @Expose
    private String gen;
    @SerializedName("syn")
    @Expose
    private List<Synonym> syn = new ArrayList<Synonym>();
    @SerializedName("mean")
    @Expose
    private List<Meaning> mean = new ArrayList<Meaning>();
    @SerializedName("ex")
    @Expose
    private List<Example> ex = new ArrayList<Example>();
}
