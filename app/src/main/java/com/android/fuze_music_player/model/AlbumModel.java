package com.android.fuze_music_player.model;

public class AlbumModel {

    private int id;
    private String name;
    private String imgUrl;
    private String artist;

    public AlbumModel(int id, String name, String imgUrl, String artist) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.artist = artist;
    }

    public AlbumModel(String albumName, Object name) {
    }

    // Getters và Setters cho các trường

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
