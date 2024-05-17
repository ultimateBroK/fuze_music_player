package com.android.fuze_music_player.service;

import com.android.fuze_music_player.model.SongHistory;
import com.android.fuze_music_player.model.SongModel;

import java.util.List;

public class SongHistoryService implements IHistoryService{

    @Override
    public List<SongHistory> showHistory() {
        return null;
    }

    @Override
    public SongHistory addToHistory(SongModel song) {
        return null;
    }

    @Override
    public void removeFromHistory(String songHistoryId) {

    }

    @Override
    public List<SongHistory> searchHistory(String keyword) {
        return null;
    }
}
