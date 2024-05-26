package com.android.fuze_music_player.service;

import com.android.fuze_music_player.model.AlbumModel;

import java.util.List;

public interface IAlbumService {

    List<AlbumModel> list();

    void createAlbum(AlbumModel albumModel);

    void updateAlbum(AlbumModel albumModel);

    /**
     * Delete by Album name
     * @param name
     */
    void deleteAlbum(String name);

}
