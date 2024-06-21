package com.android.fuze_music_player.service;

import com.android.fuze_music_player.model.ArtistModel;

import java.util.List;

public interface IArtistService {


    /**
     * Lấy ra tất cả artists
     * @return
     */
    List<ArtistModel> list();
}
