package com.qualitasvita.soundmind.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.qualitasvita.soundmind.model.Answer;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface EmotionDao {

    // Добавить эмоцию в бд
    @Insert
    void insertList(List<Answer> emotions);

    @Update
    void update(Answer emotion);

    // Удалить эмоцию из бд
    @Delete
    void delete(Answer emotion);

    // Получить все эмоции из бд
    @Query("SELECT * FROM emotions_negative")
    Single<List<Answer>> getAll();
}
