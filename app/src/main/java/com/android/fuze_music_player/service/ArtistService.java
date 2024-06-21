package com.android.fuze_music_player.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.database.SongTable;
import com.android.fuze_music_player.model.ArtistModel;

import java.util.ArrayList;
import java.util.List;

public class ArtistService implements IArtistService {

    DatabaseHelper dbHelper;

    public ArtistService(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public List<ArtistModel> list() {
        List<ArtistModel> artists = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT artist FROM " + SongTable.TABLE_NAME + " GROUP BY artist";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int index = cursor.getColumnIndex("artist");
                    String name = cursor.getString(index);
                    artists.add(new ArtistModel(name));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return artists;
    }
}
