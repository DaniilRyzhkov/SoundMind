package com.qualitasvita.soundmind;

import java.io.Serializable;

/**
 * Класс используется для представления записи эмоции и мысли в удобной форме(текст, значение).
 */
public class Answer implements Serializable {
    private String text;
    private int level;

    public Answer(String text, int level) {
        this.text = text;
        this.level = level;
    }

    public Answer(String text) {
        this.text = text;
        this.level = 0;
    }

    public Answer() {
        this.text = "";
        this.level = 0;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
