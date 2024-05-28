package com.android.fuze_music_player.service;

import android.content.Context;
import android.net.Uri;

import com.android.fuze_music_player.model.SongModel;

import java.util.List;

public interface ISongService {

    /**
     * Lấy ra các bài hát
     * @return
     */
    List<SongModel> list();

    /**
     * Import các bài hát được chọn
     * @param context
     * @param uris
     */
    void importAudioFromUris(Context context, List<Uri> uris);

    void addSong(SongModel song);

    void updateSong(SongModel song);

    void deleteSong(String songId);
}
