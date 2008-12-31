package uz.ajsoft.dictionaryb1;

/**
 * Created by Администратор on 04.04.2017.
 */
public class Word {
    private int wordId;
    private String firstWord;
    private String secondWord;

    public Word() {
    }

    public Word(int wordId, String firstWord, String secondWord) {
        this.wordId = wordId;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public int getWordId() {
        return wordId;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }
}