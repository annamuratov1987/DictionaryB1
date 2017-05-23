package uz.ajsoft.dictionaryb1;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Администратор on 20.05.2017.
 */
public class Player {
    private Context context;
    private static Player player;
    private SoundPool soundPool;
    private AssetManager assetManager;
    AssetFileDescriptor assetFileDescriptor;

    private Player(Context content){
        this.context = content;
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        assetManager = content.getAssets();
    }

    public static Player getPlayer(Context context){
        if (player==null) player = new Player(context);
        return player;
    }

    public void playById(int id){
        try {
            assetFileDescriptor = assetManager.openFd("word_sounds_folder/" + WordList.get(context).getWordById(id).getFirstWord()+".mp3");
            int soundID = soundPool.load(assetFileDescriptor, 1);
            soundPool.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f);
            Toast.makeText(context, WordList.get(context).getWordById(id).getFirstWord(), Toast.LENGTH_SHORT).show();
        }
        catch (IOException e){
            Toast.makeText(context, "Audio yozuv topilmadi.", Toast.LENGTH_LONG).show();
        }
    }


}
