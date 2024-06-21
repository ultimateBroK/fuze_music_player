package com.android.fuze_music_player.model;

import java.util.Date;

public class SongHistory {
    private Long id;
    private SongModel song;
    private Date lastPlayedAt;

    public SongHistory(SongModel song, Date lastPlayedAt) {
        this.song = song;
        this.lastPlayedAt = lastPlayedAt;
    }

    public SongHistory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SongModel getSong() {
        return song;
    }

    public void setSong(SongModel song) {
        this.song = song;
    }

    public Date getLastPlayedAt() {
        return lastPlayedAt;
    }

    public void setLastPlayedAt(Date lastPlayedAt) {
        this.lastPlayedAt = lastPlayedAt;
    }
}
