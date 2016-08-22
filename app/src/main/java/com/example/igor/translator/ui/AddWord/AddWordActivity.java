package com.example.igor.translator.ui.AddWord;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.igor.translator.R;
import com.example.igor.translator.TranslatorApp;
import com.example.igor.translator.data.WordEntry;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class AddWordActivity extends AppCompatActivity implements AddWordContract.View {
    public static final String NEW_WORD = "NEW_WORD";
    public static final int OK = 1;
    private static final String FROM_LANG_CODE = "FROM_LANG_CODE";
    private static final String TO_LANG_CODE = "TO_LANG_CODE";

    private TextView tvTranslatedWord;
    private Button btnAddWord;
    private EditText etOriginalWord;
    private WordEntry returnWordEntry;
    private ProgressBar pbSearch;
    private Spinner spinnerFromLang;
    private Spinner spinnerToLang;

    @Inject
    AddWordContract.Presenter presenter;

    private CompositeSubscription subscriptions;

    private ArrayAdapter<Lang> fromAdapter;
    private ArrayAdapter<Lang> toAdapter;
    private Lang fromLang;
    private Lang toLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TranslatorApp.getAppComponent(this).inject(this);

        setContentView(R.layout.activity_add_word);

        if (savedInstanceState != null){
            fromLang = savedInstanceState.getParcelable(FROM_LANG_CODE);
            toLang = savedInstanceState.getParcelable(TO_LANG_CODE);
        }

        etOriginalWord = (EditText) findViewById(R.id.et_original_word);
        btnAddWord = (Button) findViewById(R.id.add_word);
        tvTranslatedWord = (TextView) findViewById(R.id.tv_translated_word);
        pbSearch = (ProgressBar) findViewById(R.id.pb_search);
        spinnerFromLang = (Spinner) findViewById(R.id.spinner_from_lang);
        spinnerToLang = (Spinner) findViewById(R.id.spinner_to_lang);

        btnAddWord.setOnClickListener(btnAddWordClick);

        setSpinnerAdapters();
    }

    private void setSpinnerAdapters() {
        List<Lang> fromLanguages = new ArrayList<>();
        fromAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fromLanguages);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromLang.setAdapter(fromAdapter);
        spinnerFromLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Lang lang = fromLanguages.get((int)l);
                presenter.fromLangSelected(lang.code());
                fromLang = lang;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<Lang> toLanguages = new ArrayList<>();
        toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, toLanguages);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToLang.setAdapter(toAdapter);
        spinnerToLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toLang = toLanguages.get(i);
                makeSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private View.OnClickListener btnAddWordClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra(NEW_WORD, returnWordEntry);
            AddWordActivity.this.setResult(OK, intent);
            finish();
        }
    };

    private void makeSearch(){
        presenter.searchString(etOriginalWord.getText().toString(), fromLang.code(), toLang.code());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(FROM_LANG_CODE, fromLang);
        outState.putParcelable(TO_LANG_CODE, toLang);
    }

    @Override
    protected void onResume() {
        super.onResume();

        subscriptions = new CompositeSubscription();
        subscriptions.add(
            RxTextView.afterTextChangeEvents(etOriginalWord)
                .doOnNext(n -> clearErrorAndTranslation())
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(changes -> !changes.editable().toString().isEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> makeSearch())
        );

        presenter.attachView(this);
        presenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        subscriptions.unsubscribe();
        presenter.detachView();
    }

    @Override
    public void setReturnWordEntry(WordEntry returnWordEntry) {
        this.returnWordEntry = returnWordEntry;
    }

    @Override
    public void setTranslatedWord(String translatedWord) {
        tvTranslatedWord.setText(translatedWord);
    }

    @Override
    public void clearErrorAndTranslation(){
        addWordButtonSetEnabled(false);
        setTranslatedWord("");
    }

    @Override
    public void setWordNotFound() { tvTranslatedWord.setText("..."); }

    @Override
    public void addWordButtonSetEnabled(boolean enabled){
        btnAddWord.setEnabled(enabled);
    }

    @Override
    public void pbSearchSetEnabled(boolean enabled) { pbSearch.setVisibility(enabled?View.VISIBLE:View.INVISIBLE); }

    @Override
    public void setFromLanguages(ArrayList<Lang> fromLangs) {
        fromAdapter.clear();
        fromAdapter.addAll(fromLangs);

        if (this.fromLang != null) {
            spinnerFromLang.setSelection(fromAdapter.getPosition(this.fromLang));
        }
    }

    @Override
    public void setToLanguages(ArrayList<Lang> toLanguages) {
        toAdapter.clear();
        toAdapter.addAll(toLanguages);

        if (this.toLang != null) {
            spinnerToLang.setSelection(toAdapter.getPosition(this.toLang));
        }
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(
                findViewById(R.id.activity_add_word),
                R.string.error_network,
                Snackbar.LENGTH_SHORT).show();
    }
}
