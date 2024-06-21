package com.android.fuze_music_player.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.database.SongTable;
import com.android.fuze_music_player.model.AlbumModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumService implements IAlbumService {

    DatabaseHelper dbHelper;

    public AlbumService(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public List<AlbumModel> list() {
        List<AlbumModel> albums = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT album FROM " + SongTable.TABLE_NAME + " GROUP BY album";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int index = cursor.getColumnIndex("album");
                    String albumName = cursor.getString(index);


                    albums.add(new AlbumModel(albumName, null));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return albums;
    }
}
