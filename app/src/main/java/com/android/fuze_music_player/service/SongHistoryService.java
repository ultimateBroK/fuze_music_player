package com.android.fuze_music_player.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.database.SongHistoryTable;
import com.android.fuze_music_player.database.SongTable;
import com.android.fuze_music_player.model.SongHistory;
import com.android.fuze_music_player.model.SongModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SongHistoryService implements IHistoryService {

    DatabaseHelper dbHelper;

    public SongHistoryService(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public List<SongHistory> showHistory() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<SongHistory> historyList = new ArrayList<>();

        String query = "SELECT sh." + SongHistoryTable.KEY_ID + ", " +
                "sh." + SongHistoryTable.KEY_LAST_PLAYED_AT + ", " +
                "s." + SongTable.KEY_ID + ", " +
                "s." + SongTable.KEY_TITLE + ", " +
                "s." + SongTable.KEY_ARTIST + ", " +
                "s." + SongTable.KEY_ALBUM + ", " +
                "s." + SongTable.KEY_GENRE + ", " +
                "s." + SongTable.KEY_DURATION + ", " +
                "s." + SongTable.KEY_IMG_URL +
                " FROM " + SongHistoryTable.TABLE_NAME + " sh" +
                " JOIN " + SongTable.TABLE_NAME + " s ON sh." + SongHistoryTable.KEY_SONG_ID + " = s." + SongTable.KEY_ID +
                " ORDER BY sh." + SongHistoryTable.KEY_LAST_PLAYED_AT + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                SongHistory songHistory = new SongHistory();
                songHistory.setId(cursor.getLong(cursor.getColumnIndexOrThrow(SongHistoryTable.KEY_ID)));
                songHistory.setLastPlayedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(SongHistoryTable.KEY_LAST_PLAYED_AT))));
                SongModel song = new SongModel();
                song.setId(cursor.getLong(cursor.getColumnIndexOrThrow(SongTable.KEY_ID)));
                song.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_TITLE)));
                song.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_ARTIST)));
                song.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_ALBUM)));
                song.setDuration(cursor.getLong(cursor.getColumnIndexOrThrow(SongTable.KEY_DURATION)));

                songHistory.setSong(song);

                historyList.add(songHistory);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return historyList;
    }

    @Override
    public SongHistory addToHistory(SongModel song) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SongHistoryTable.KEY_SONG_ID, song.getId());
        values.put(SongHistoryTable.KEY_LAST_PLAYED_AT, System.currentTimeMillis());

        long newRowId = db.insert(SongHistoryTable.TABLE_NAME, null, values);

        if (newRowId == -1) {
            return null;  // insertion failed
        } else {
            return new SongHistory(song, new Date());
        }
    }
}
