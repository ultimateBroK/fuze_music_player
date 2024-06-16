package com.android.fuze_music_player.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.fuze_music_player.R;
import com.android.fuze_music_player.adapter.SongAdapter;
import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.model.SongModel;
import com.android.fuze_music_player.service.SongService;

import java.util.ArrayList;
import java.util.List;

public class SongsFragment extends Fragment {
    private Button btnSelectDir;
    private ActivityResultLauncher<Intent> pickSongsLauncher;

    private RecyclerView songsRecyclerView;
    private SongAdapter songAdapter;
    private List<SongModel> songsList = new ArrayList<>();

    public SongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_songs, container, false);

        songsRecyclerView = view.findViewById(R.id.songs_List);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        songAdapter = new SongAdapter(getContext(), songsList);
        songsRecyclerView.setAdapter(songAdapter);

        // Load danh sách bài hát từ cơ sở dữ liệu (sử dụng SongService)
        loadSongsFromDatabase();

        return view;
    }

    // Phương thức để load danh sách bài hát từ cơ sở dữ liệu
    private void loadSongsFromDatabase() {
        // Tạo instance của DatabaseHelper (nếu chưa có)
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        // Tạo instance của SongService và lấy danh sách bài hát
        SongService songService = new SongService(dbHelper);
        List<SongModel> songs = songService.list();

        // Cập nhật RecyclerView với danh sách bài hát
        songsList.clear();
        songsList.addAll(songs);
        songAdapter.updateData(songsList);
    }
}

