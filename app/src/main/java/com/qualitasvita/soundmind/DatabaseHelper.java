package com.qualitasvita.soundmind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Вспомогательный класс, обеспечивающий работу базы данных.
 * _id на данный момент не используется.
 * При внесении изменений вся база данных переписывается, позже будет исправленно на апгрейд бд.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "note_store.db";
    private static final int SCHEMA = 2;//version
    static final String TABLE = "notes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_SITUATION = "situation";
    public static final String COLUMN_EMOTION_TEXT = "emotionText";
    public static final String COLUMN_THOUGHT_TEXT = "thoughtText";
    public static final String COLUMN_ACTION_TEXT = "actionText";
    public static final String COLUMN_RESPONSE_TEXT = "responseText";
    public static final String COLUMN_RESULT_TEXT = "resultText";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT, "
                + COLUMN_SITUATION + " TEXT, "
                + COLUMN_EMOTION_TEXT + " TEXT, "
                + COLUMN_THOUGHT_TEXT + " TEXT, "
                + COLUMN_ACTION_TEXT + " TEXT, "
                + COLUMN_RESPONSE_TEXT + " TEXT, "
                + COLUMN_RESULT_TEXT + " TEXT" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
