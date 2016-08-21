package com.example.igor.translator.ui.List;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.igor.translator.R;
import com.example.igor.translator.data.WordEntry;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by igor on 20.08.16.
 *
 */

class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    List<WordEntry> wordEntries = new ArrayList<>();
    ArrayList<WordEntry> filteredWordEntries = new ArrayList<>();

    boolean isFiltering = false;

    WordListContract.View view;

    WordListAdapter(WordListContract.View view){
        this.view = view;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WordViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.word_item_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        WordEntry wordEntry = isFiltering? filteredWordEntries.get(position):wordEntries.get(position);

        holder.word.setText(wordEntry.wordOriginal());
        holder.translation.setText(wordEntry.wordTranslation());

        if (wordEntry.partOfSpeech() != null && !wordEntry.partOfSpeech().isEmpty()) {
            holder.pos.setText(wordEntry.partOfSpeech());
            holder.pos.setVisibility(View.VISIBLE);
        } else {
            holder.pos.setVisibility(View.GONE);
        }

        if (wordEntry.wordTranscription() != null) {
            holder.transcription.setVisibility(View.VISIBLE);
            holder.transcription.setText(String.format("[ %s ]", wordEntry.wordTranscription()));
        } else {
            holder.transcription.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return isFiltering? filteredWordEntries.size():wordEntries.size();
    }

    void setFilter(String query) {
        if (query == null || query.isEmpty()){
            isFiltering = false;
            filteredWordEntries.clear();
        } else {
            isFiltering = true;
            filteredWordEntries.clear();

            Observable.from(wordEntries)
                    .filter(e -> e.wordOriginal().toLowerCase().contains(query)
                            || e.wordTranslation().toLowerCase().contains(query))
                    .toList()
                    .subscribe(l -> filteredWordEntries.addAll(l));

            notifyDataSetChanged();
        }
    }

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView cv;
        private final TextView translation;
        private final TextView word;
        private final TextView pos;
        private final TextView transcription;
        private final ImageButton deleteWord;

        WordViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            word = (TextView) itemView.findViewById(R.id.word);
            translation = (TextView) itemView.findViewById(R.id.translation);
            pos = (TextView) itemView.findViewById(R.id.part_of_speech);
            transcription = (TextView) itemView.findViewById(R.id.transcription);
            deleteWord = (ImageButton) itemView.findViewById(R.id.ib_delete_word);

            deleteWord.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == deleteWord) {
                onDeleteClick(getAdapterPosition());
            }
        }
    }

    private void onDeleteClick(int adapterPosition) {
        view.removeItem(getEntry(adapterPosition));
    }

    WordEntry getEntry(int pos) {
        return isFiltering? filteredWordEntries.get(pos): wordEntries.get(pos);
    }

    public void addWord(WordEntry model) {
        wordEntries.add(model);
        notifyItemInserted(wordEntries.size());
    }

    public void remove(WordEntry model) {
        int pos = wordEntries.indexOf(model);
        wordEntries.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setWordList(List<WordEntry> wordList) {
        wordEntries = wordList;
        notifyDataSetChanged();
    }
}
