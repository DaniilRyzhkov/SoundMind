package com.qualitasvita.soundmind.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Класс используется для представления записи эмоции и мысли в удобной форме(текст, значение).
 */
@Entity(tableName = "emotions_negative")
data class Answer(
        @PrimaryKey
        @ColumnInfo(name = "emotion")
        var text: String) {

    @ColumnInfo(name = "emotion_level")
    var level: Int = 0

    @Ignore
    constructor(text: String, level: Int) : this(text) {
        this.text = text
        this.level = level
    }
}