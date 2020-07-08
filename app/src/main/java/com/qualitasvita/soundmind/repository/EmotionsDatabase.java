package com.qualitasvita.soundmind.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.qualitasvita.soundmind.model.Answer;

@Database(entities = {Answer.class}, version = 1)
public abstract class EmotionsDatabase extends RoomDatabase {

    public abstract EmotionDao getEmotionDao();
}
