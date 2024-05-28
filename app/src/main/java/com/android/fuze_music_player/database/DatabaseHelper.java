package com.android.fuze_music_player.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fuze_music";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create songs table
        String create_songs_table = String.format(
                "CREATE TABLE IF NOT EXISTS %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT UNIQUE)",
                SongTable.TABLE_NAME,
                SongTable.KEY_ID,
                SongTable.KEY_TITLE,
                SongTable.KEY_ARTIST,
                SongTable.KEY_GENRE,
                SongTable.KEY_ALBUM,
                SongTable.KEY_DURATION,
                SongTable.KEY_IMG_URL,
                SongTable.KEY_PATH
        );
        db.execSQL(create_songs_table);

        // Create song history table
        String create_song_history_table = String.format(
                "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, FOREIGN KEY (%s) REFERENCES %s(%s))",
                SongHistoryTable.TABLE_NAME,
                SongHistoryTable.KEY_ID,
                SongHistoryTable.KEY_SONG_ID,
                SongHistoryTable.KEY_LAST_PLAYED_AT,
                SongHistoryTable.KEY_SONG_ID,
                SongTable.TABLE_NAME,
                SongTable.KEY_ID
        );
        db.execSQL(create_song_history_table);

        // Create albums table
        String create_album_table = String.format(
                "CREATE TABLE IF NOT EXISTS %s (%s TEXT PRIMARY KEY, %s TEXT)",
                AlbumTable.TABLE_NAME,
                AlbumTable.KEY_ID,
                AlbumTable.KEY_IMG_URL
        );
        db.execSQL(create_album_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SongTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SongHistoryTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AlbumTable.TABLE_NAME);
        onCreate(db);
    }
}
