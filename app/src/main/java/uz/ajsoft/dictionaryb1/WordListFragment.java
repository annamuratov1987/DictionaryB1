package uz.ajsoft.dictionaryb1;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * Created by Администратор on 30.03.2017.
 */
public class WordListFragment extends Fragment {

    private EditText wordSearch;
    private RecyclerView wordListRecyclerView;
    private WordAdapter wordAdapter;
    private WordList wordList;
    private List<Word> words;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);

        wordSearch = (EditText) view.findViewById(R.id.word_search);
        wordSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                words = wordList.getSearchWords(String.valueOf(s));
                wordAdapter.setWordList(words);
                wordListRecyclerView.setAdapter(wordAdapter);
                wordAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        wordListRecyclerView = (RecyclerView) view.findViewById(R.id.word_list_recycler_view);
        wordListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        wordList = WordList.get(getContext());
        words = wordList.getWords();
        wordAdapter = new WordAdapter(words);
        wordListRecyclerView.setAdapter(wordAdapter);

        return view;
    }

    //ViewHolder
    private class WordViewHolder extends RecyclerView.ViewHolder{
        private TextView wordFirst;
        private TextView wordSecond;
        private ImageView soundIcon;
        private SoundPool soundPool;

        public WordViewHolder(View itemView) {
            super(itemView);
            wordFirst = (TextView) itemView.findViewById(R.id.word_list_item_word_first);
            wordSecond = (TextView) itemView.findViewById(R.id.word_list_item_word_second);
            soundIcon = (ImageView) itemView.findViewById(R.id.word_list_item_sound_icon);
            soundIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Player.getPlayer(getContext()).playById(v.getId());
                }
            });

        }
    }

    //Adapter
    private class WordAdapter extends RecyclerView.Adapter<WordViewHolder>{

        private List<Word> wordList;

        public WordAdapter(List<Word> wordList) {
            this.wordList = wordList;
        }

        @Override
        public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.word_list_item, parent, false);
            return new WordViewHolder(view);
        }

        @Override
        public void onBindViewHolder(WordViewHolder holder, int position) {
            Word word = wordList.get(position);
            holder.wordFirst.setText(word.getFirstWord());
            holder.wordSecond.setText(word.getSecondWord());
            holder.soundIcon.setId(word.getWordId());
        }

        @Override
        public int getItemCount() {
            return wordList.size();
        }

        public void setWordList(List<Word> wordList){
            this.wordList = wordList;
        }

    }
}
