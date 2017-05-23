package uz.ajsoft.dictionaryb1;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Администратор on 02.05.2017.
 */
public class WordBaseHelper extends SQLiteOpenHelper{

    private Context context;
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "DbWord.db";

    public WordBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+WordDbSchema.TableWord.NAME
                + "("+"_id integer primary key autoincrement, "
                + WordDbSchema.TableWord.COL_FIRST_WORD + ", "
                + WordDbSchema.TableWord.COL_SECOND_WORD + ")");

        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("database_folder/db.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (bufferedReader.ready()){
                String s = bufferedReader.readLine();
                String[] strings = s.split("\t");
                ContentValues contentValues = new ContentValues();
                contentValues.put(WordDbSchema.TableWord.COL_FIRST_WORD, strings[0]);
                contentValues.put(WordDbSchema.TableWord.COL_SECOND_WORD, strings[1]);
                db.insert(WordDbSchema.TableWord.NAME, null, contentValues);
            }
            assetManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
