package com.qualitasvita.soundmind.di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.qualitasvita.soundmind.repository.EmotionsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private EmotionsDatabase db;

    public DatabaseModule(@NonNull Context context) {
        db = Room.databaseBuilder(context, EmotionsDatabase.class, "database").build();
    }

    @Singleton
    @Provides
    public EmotionsDatabase provideDatabase() {
        return db;
    }
}


