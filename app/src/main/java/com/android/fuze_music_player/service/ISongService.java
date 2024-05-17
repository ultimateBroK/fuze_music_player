package com.android.fuze_music_player.service;

import com.android.fuze_music_player.model.SongModel;

public interface ISongService {

    SongModel addSong(SongModel song);

    SongModel updateSong(SongModel song);

    void deleteSong(String songId);
}
