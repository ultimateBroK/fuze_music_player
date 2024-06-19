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
import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.model.AlbumModel;
import com.android.fuze_music_player.service.AlbumService;

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

        // Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.Albums_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize album list and adapter
        albumList = new ArrayList<>();
        albumAdapter = new AlbumAdapter(getContext(), albumList);
        recyclerView.setAdapter(albumAdapter);

        // Initialize AlbumService with DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        albumService = new AlbumService(dbHelper);

        // Load albums list initially
        loadAlbums();

        return rootView;
    }

    private void loadAlbums() {
        // Get albums list from AlbumService
        List<AlbumModel> albums = albumService.list();

        // Update albums list in adapter
        albumList.clear();
        albumList.addAll(albums);
        albumAdapter.notifyDataSetChanged();
    }

    // Method to update albums list from outside this fragment
    public void updateAlbums() {
        // Reload albums list
        loadAlbums();
    }

    // Example method to trigger update from another part of your app
    private void addNewSongAndUpdateAlbums() {
        // Perform your logic to add a new song
        // Then update albums list
        updateAlbums();
    }
}
