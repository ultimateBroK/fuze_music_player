package com.android.fuze_music_player.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.fuze_music_player.R;
import com.android.fuze_music_player.adapter.AlbumAdapter;
import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.model.AlbumModel;
import com.android.fuze_music_player.service.AlbumService;
import com.android.fuze_music_player.service.IAlbumService;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends Fragment {

    private RecyclerView albumsRecyclerView;
    private AlbumAdapter albumAdapter;
    private List<AlbumModel> albumsList = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private IAlbumService albumService;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);

        albumsRecyclerView = view.findViewById(R.id.albums_List);
        albumsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DatabaseHelper(getContext());
        albumService = new AlbumService(databaseHelper);

        // Load albums from database
        loadAlbumsFromDatabase();

        return view;
    }

    public void updateAlbums() {
        loadAlbumsFromDatabase();
    }

    private void loadAlbumsFromDatabase() {
        List<AlbumModel> albums = albumService.list();

        albumsList.clear();
        albumsList.addAll(albums);

        if (albumAdapter == null) {
            albumAdapter = new AlbumAdapter(getContext(), albumsList);
            albumsRecyclerView.setAdapter(albumAdapter);
        } else {
            albumAdapter.notifyDataSetChanged();
        }
    }
}
