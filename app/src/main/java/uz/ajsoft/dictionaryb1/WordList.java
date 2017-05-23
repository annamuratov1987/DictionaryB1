package uz.ajsoft.dictionaryb1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 04.04.2017.
 */
public class WordList {
    private static WordList wordList;
    private Context contextWordList;
    private SQLiteDatabase dbWord;



    private WordList(Context context) {
        contextWordList = context.getApplicationContext();
        dbWord = new WordBaseHelper(contextWordList).getWritableDatabase();
    }
    public static WordList get(Context context) {
        if (wordList == null) wordList = new WordList(context);
        return wordList;
    }

    private List<Word> getWords(String selections, String[] selectionArgs){
        List<Word> list = new ArrayList<>();
        Word word;
        Cursor cursor = dbWord.query(WordDbSchema.TableWord.NAME, null, selections, selectionArgs, null, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                word = new Word();
                word.setWordId(cursor.getInt(0));
                word.setFirstWord(cursor.getString(1));
                word.setSecondWord(cursor.getString(2));
                list.add(word);
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return list;
    }

    public List<Word> getSearchWords(String searchText){
        String selections = WordDbSchema.TableWord.COL_FIRST_WORD + " LIKE ?";
        String[] selectionArgs = {"%"+searchText+"%"};
        return getWords(selections, selectionArgs);
    }

    public List<Word> getWords(){
        return getWords(null, null);
    }

    public Word getWordById(int id){
        Word word;
        String selections = WordDbSchema.TableWord.COL_ID +" = ?";
        String[] selectionArgs = {""+id};
        Cursor cursor = dbWord.query(WordDbSchema.TableWord.NAME, null, selections, selectionArgs, null, null, null);
        try {
            word = new Word();
            cursor.moveToFirst();
            word.setWordId(cursor.getInt(0));
            word.setFirstWord(cursor.getString(1));
            word.setSecondWord(cursor.getString(2));
        }finally {
            cursor.close();
        }
        return word;
    }
}
