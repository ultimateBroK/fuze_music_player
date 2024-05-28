package com.android.fuze_music_player.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.database.SongHistoryTable;
import com.android.fuze_music_player.database.SongTable;
import com.android.fuze_music_player.model.SongModel;

import java.util.ArrayList;
import java.util.List;

public class SongService implements ISongService {

    DatabaseHelper dbHelper;

    public SongService(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public List<SongModel> list() {
        List<SongModel> songList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "songs",
                null,
                null,
                null,
                null,
                null,
                "title ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                SongModel song = new SongModel();
                song.setId(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_ID)));
                song.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_TITLE)));
                song.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_ARTIST)));
                song.setGenre(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_GENRE)));
                song.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_ALBUM)));
                song.setDuration(cursor.getLong(cursor.getColumnIndexOrThrow(SongTable.KEY_DURATION)));
                song.setImgUrl(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_IMG_URL)));
                song.setPath(cursor.getString(cursor.getColumnIndexOrThrow(SongTable.KEY_PATH)));
                songList.add(song);
            }
            cursor.close();
        }

        return songList;
    }

    @Override
    public void importAudioFromUris(Context context, List<Uri> uris) {
        Log.i("SongService", "Uris " + uris);
        List<SongModel> songList = new ArrayList<>();

        for (Uri uri : uris) {
            try {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();

                mmr.setDataSource(context, uri);

                // Retrieve and log all available metadata
                String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                String genre = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
                String mimeType = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);

                Log.d("SongService", "Title: " + title);
                Log.d("SongService", "Artist: " + artist);
                Log.d("SongService", "Album: " + album);
                Log.d("SongService", "Duration: " + duration);
                Log.d("SongService", "Genre: " + genre);
                Log.d("SongService", "MimeType: " + mimeType);

                SongModel song = new SongModel();
                song.setTitle(title != null ? title : "Unknown Title");
                song.setArtist(artist != null ? artist : "Unknown Artist");
                song.setAlbum(album != null ? album : "Unknown Album");
                song.setDuration(duration != null ? Long.parseLong(duration) : 0);
                song.setGenre(genre != null ? genre : "Unknown Genre");
                song.setPath(uri.getPath());

                songList.add(song);

                mmr.release();
            } catch (Exception e) {
                Log.e("SongService", "Failed to retrieve metadata for uri: " + uri, e);
            }
        }

        songList.forEach(this::addSong);
    }


    @Override
    public void addSong(SongModel song) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        boolean isExist = this.checkSongExist(db, song);
        if (isExist) return;

        ContentValues values = getContentValues(song);

        try {
            long newRowId = db.replace("songs", null, values);
            if (newRowId == -1) {
                Log.e("SongService", "Failed to add song: " + song.getTitle());
            } else {
                Log.i("SongService", "Song added with Row ID: " + newRowId);
                Log.i("SongService", "Song added with Path: " + song.getPath());
            }
        } catch (Exception e) {
            Log.e("SongService", "Failed to add song: " + e);
        }
    }

    @NonNull
    private static ContentValues getContentValues(SongModel song) {
        ContentValues values = new ContentValues();
        values.put(SongTable.KEY_ID, song.getId());
        values.put(SongTable.KEY_TITLE, song.getTitle());
        values.put(SongTable.KEY_ARTIST, song.getArtist());
        values.put(SongTable.KEY_ALBUM, song.getAlbum());
        values.put(SongTable.KEY_GENRE, song.getGenre());
        values.put(SongTable.KEY_DURATION, song.getDuration());
        values.put(SongTable.KEY_IMG_URL, song.getImgUrl());
        values.put(SongTable.KEY_PATH, song.getPath());
        return values;
    }

    private boolean checkSongExist(SQLiteDatabase db, SongModel song) {
        String selection = SongTable.KEY_ID + " = ?";
        String[] selectionArgs = {song.getPath()};

        Cursor cursor = db.query("songs", new String[]{SongTable.KEY_ID}, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Log.i("SongService", "Song with path already exists: " + song.getPath());
            cursor.close();
            return true;
        }

        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    @Override
    public void deleteSong(String songId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Start a transaction to ensure both deletions happen atomically
        db.beginTransaction();
        try {
            // Delete from song_history first
            String historySelection = SongHistoryTable.KEY_SONG_ID + " = ?";
            String[] historySelectionArgs = {songId};

            int deletedHistoryRows = db.delete(SongHistoryTable.TABLE_NAME, historySelection, historySelectionArgs);
            Log.i("SongService", "Deleted " + deletedHistoryRows + " entries from song_history for song ID: " + songId);

            // Delete from songs
            String songSelection = SongTable.KEY_ID + " = ?";
            String[] songSelectionArgs = {songId};

            int deletedSongRows = db.delete(SongTable.TABLE_NAME, songSelection, songSelectionArgs);
            if (deletedSongRows > 0) {
                Log.i("SongService", "Song deleted with ID: " + songId);
            } else {
                Log.i("SongService", "No song found with ID: " + songId);
            }

            // Mark the transaction as successful
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("SongService", "Failed to delete song: " + e);
        } finally {
            // End the transaction
            db.endTransaction();
        }
    }

}
