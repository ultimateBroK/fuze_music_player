package com.android.fuze_music_player.model;

import java.util.Date;

public class SongHistory {
    private SongModel song;
    private Date lastPlayedAt;

    public SongHistory(SongModel song, Date lastPlayedAt) {
        this.song = song;
        this.lastPlayedAt = lastPlayedAt;
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
