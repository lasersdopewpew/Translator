package com.example.igor.translator.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.igor.translator.R;
import com.example.igor.translator.WordEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WordListView {

    WordListAdapter wordListAdapter = new WordListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView rvWordList = (RecyclerView) findViewById(R.id.wordList);
        if (rvWordList != null) {
            rvWordList.setLayoutManager(layoutManager);
            rvWordList.setAdapter(wordListAdapter);
        }

    }

    @Override
    public void setWorldList(ArrayList<WordEntry> worldList) {
        wordListAdapter.setWordList(worldList);
    }


    class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>{
        ArrayList<WordEntry> wordList = new ArrayList<>();

        public void setWordList(ArrayList<WordEntry> wordList) {
            this.wordList = wordList;
            notifyDataSetChanged();
        }

        @Override
        public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WordViewHolder(
                    LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.word_layout, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(WordViewHolder holder, int position) {
            WordEntry wordEntry = wordList.get(position);

            holder.word.setText(wordEntry.wordOriginal);
            holder.translation.setText(wordEntry.wordTranslation);
        }

        @Override
        public int getItemCount() {
            return wordList.size();
        }

        class WordViewHolder extends RecyclerView.ViewHolder {
            private final CardView cv;
            private final TextView translation;
            private final TextView word;

            WordViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.cv);
                word = (TextView)itemView.findViewById(R.id.word);
                translation = (TextView) itemView.findViewById(R.id.translation);
            }
        }

    }
}
