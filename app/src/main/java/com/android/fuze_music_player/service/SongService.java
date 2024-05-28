package com.android.fuze_music_player.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import com.android.fuze_music_player.database.DatabaseHelper;
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
        return null;
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
    public SongModel addSong(SongModel song) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        boolean isExist = this.checkSongExist(db, song);
        if (isExist) return song;

        ContentValues values = new ContentValues();
        values.put("id", song.getId());
        values.put("title", song.getTitle());
        values.put("artist", song.getArtist());
        values.put("album", song.getAlbum());
        values.put("genre", song.getGenre());
        values.put("duration", song.getDuration());
        values.put("imgUrl", song.getImgUrl());
        values.put("path", song.getPath());

        try {
            long newRowId = db.replace("songs", null, values);
            if (newRowId == -1) {
                Log.e("SongService", "Failed to add song: " + song.getTitle());
                return null;
            } else {
                Log.i("SongService", "Song added with ID: " + newRowId);
                return song;
            }
        } catch (Exception e) {
            Log.e("SongService", "Failed to add song: " + e);
            return null;
        }
    }

    private boolean checkSongExist(SQLiteDatabase db, SongModel song) {
        String selection = "path = ?";
        String[] selectionArgs = {song.getPath()};

        Cursor cursor = db.query("songs", new String[]{"id"}, selection, selectionArgs, null, null, null);

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
    public SongModel updateSong(SongModel song) {
        return null;
    }

    @Override
    public void deleteSong(String songId) {

    }
}
