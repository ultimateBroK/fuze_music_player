package com.android.fuze_music_player.service;

import com.android.fuze_music_player.model.SongHistory;
import com.android.fuze_music_player.model.SongModel;

import java.util.List;

public interface IHistoryService {

    List<SongHistory> showHistory();

    SongHistory addToHistory(SongModel song);

    void removeFromHistory(String songHistoryId);

    List<SongHistory> searchHistory(String keyword);
}
