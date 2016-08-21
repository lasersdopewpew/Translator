package com.example.igor.translator.ui.List;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;

import com.example.igor.translator.TranslatorApp;
import com.example.igor.translator.R;
import com.example.igor.translator.data.WordEntry;
import com.example.igor.translator.ui.AddWord.AddWordActivity;

import java.util.List;

import javax.inject.Inject;

public class WordListActivity extends AppCompatActivity implements WordListContract.View {

    private static final int ADD_WORD = 1;

    @Inject
    WordListContract.Presenter wordListPresenter;

    private WordListAdapter wordListAdapter = new WordListAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TranslatorApp.getAppComponent(this).inject(this);

        setContentView(R.layout.activity_word_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView rvWordList = (RecyclerView) findViewById(R.id.wordList);
        if (rvWordList != null) {
            rvWordList.setLayoutManager(layoutManager);
            rvWordList.setAdapter(wordListAdapter);
        }

        FloatingActionButton fabAddWord = (FloatingActionButton) findViewById(R.id.fabAddWord);
        fabAddWord.setOnClickListener(fabAddWordListener);

        SearchView svWordSearch = (SearchView) findViewById(R.id.svWordSearch);


        svWordSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                wordListAdapter.setFilter(newText);
                Log.e("NEWTEXT", newText);
                return true;
            }
        });

        wordListPresenter.setView(this);
    }


    View.OnClickListener fabAddWordListener = v -> {
        Intent intent = new Intent(WordListActivity.this, AddWordActivity.class);
        WordListActivity.this.startActivityForResult(intent, ADD_WORD);
    };

    @Override
    public void setWorldList(List<WordEntry> worldList) {
        wordListAdapter.setWordList(worldList);
    }

    @Override
    public void removeItem(WordEntry wordEntry) {
        wordListPresenter.removeWordEntry(wordEntry);
    }

    @Override
    public void addWord(WordEntry wordEntry) {
        wordListAdapter.addWord(wordEntry);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_WORD && resultCode == AddWordActivity.OK) {
            WordEntry wordEntry = data.getParcelableExtra(AddWordActivity.NEW_WORD);
            wordListPresenter.addWordEntry(wordEntry);
        }
    }
}
