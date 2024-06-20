package com.android.fuze_music_player.service;

import com.android.fuze_music_player.model.AlbumModel;

import java.util.List;

public interface IAlbumService {

    /**
     * Lấy ra tất cả album
     * @return
     */
    List<AlbumModel> list();
}
