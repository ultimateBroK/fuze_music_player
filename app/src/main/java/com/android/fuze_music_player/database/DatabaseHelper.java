package com.android.fuze_music_player.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fuze_music";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create songs table
        String create_songs_table = String.format(
                "CREATE TABLE IF NOT EXISTS %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT)",
                SongTable.TABLE_NAME,
                SongTable.KEY_ID,
                SongTable.KEY_TITLE,
                SongTable.KEY_ARTIST,
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


        // Create songs table
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
        // Drop songs table if exists
        String drop_songs_table = String.format("DROP TABLE IF EXISTS %s", SongTable.TABLE_NAME);
        db.execSQL(drop_songs_table);

        // Drop song history table if exists
        String drop_song_history_table = String.format("DROP TABLE IF EXISTS %s", SongHistoryTable.TABLE_NAME);
        db.execSQL(drop_song_history_table);

        // Recreate tables
        onCreate(db);
    }
}
