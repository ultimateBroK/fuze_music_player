package com.android.fuze_music_player.model;

public class AlbumModel {

    private String name;
    private String imgUrl; // Cho phép imgUrl có thể null
    private String artist;

    public AlbumModel(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.imgUrl = null; // Giá trị mặc định hoặc xử lý trường hợp null
    }

    public AlbumModel(String name, String imgUrl, String artist) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.artist = artist;
    }

    // Getters và Setters cho các trường

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
