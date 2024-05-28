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

    void importAudioFromUris(Context context, List<Uri> uris);

    SongModel addSong(SongModel song);

    SongModel updateSong(SongModel song);

    void deleteSong(String songId);
}
