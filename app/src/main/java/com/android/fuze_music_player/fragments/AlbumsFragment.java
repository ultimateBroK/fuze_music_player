package com.android.fuze_music_player.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.fuze_music_player.R;
import com.android.fuze_music_player.adapter.AlbumAdapter;
import com.android.fuze_music_player.model.AlbumModel;
import com.android.fuze_music_player.service.AlbumService;
import com.android.fuze_music_player.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    private List<AlbumModel> albumList;
    private AlbumService albumService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_albums, container, false);

        // Khởi tạo RecyclerView
        recyclerView = rootView.findViewById(R.id.Albums_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách album và adapter
        albumList = new ArrayList<>();
        albumAdapter = new AlbumAdapter(getContext(), albumList);
        recyclerView.setAdapter(albumAdapter);

        // Khởi tạo AlbumService với DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        albumService = new AlbumService(dbHelper);

        // Load danh sách album
        loadAlbums();

        return rootView;
    }

    private void loadAlbums() {
        // Lấy danh sách album từ AlbumService
        List<AlbumModel> albums = albumService.list();

        // Cập nhật danh sách album trong adapter
        albumList.clear();
        albumList.addAll(albums);
        albumAdapter.notifyDataSetChanged();
    }
}
