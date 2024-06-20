package com.android.fuze_music_player.model;

public class AlbumModel {

    private String name;
    private String imgUrl; // Cho phép imgUrl có thể null

    public AlbumModel(String name) {
        this.name = name;
        this.imgUrl = null; // Giá trị mặc định hoặc xử lý trường hợp null
    }

    public AlbumModel(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
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
}
