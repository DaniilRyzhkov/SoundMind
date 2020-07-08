package com.qualitasvita.soundmind.model;

import java.io.Serializable;

/**
 * Класс представляет объект записи
 */
public class Note implements Serializable {
    private String date;
    private String situationText;
    private String emotionText;// Может стоит использовать список вместо строки?
    private String thoughtText;// Может стоит использовать список вместо строки?
    private String actionText;
    private String responseText;
    private String resultText;

    public Note(String date, String situationText, String emotionText, String thoughtText, String actionText, String responseText, String resultText) {
        this.date = date;
        this.situationText = situationText;
        this.emotionText = emotionText;
        this.thoughtText = thoughtText;
        this.actionText = actionText;
        this.responseText = responseText;
        this.resultText = resultText;
    }

    public Note() {
    }

    public String getDate() {
        return date;
    }

    public String getSituationText() {
        return situationText;
    }

    public String getEmotionText() {
        return emotionText;
    }

    public String getThoughtText() {
        return thoughtText;
    }

    public String getActionText() {
        return actionText;
    }

    public String getResponseText() {
        return responseText;
    }

    public String getResultText() {
        return resultText;
    }
}
