package com.android.fuze_music_player.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.android.fuze_music_player.adapter.ArtistAdapter;
import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.model.ArtistModel;
import com.android.fuze_music_player.service.ArtistService;
import com.android.fuze_music_player.service.IArtistService;

import java.util.ArrayList;
import java.util.List;

public class ArtistsFragment extends Fragment {

    private RecyclerView artistsRecyclerView;
    private ArtistAdapter artistAdapter;
    private List<ArtistModel> artistsList = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private IArtistService artistService;

    public ArtistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artists, container, false);

        artistsRecyclerView = view.findViewById(R.id.artists_List);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        artistAdapter = new ArtistAdapter(getContext(), artistsList);
        artistsRecyclerView.setAdapter(artistAdapter);

        databaseHelper = new DatabaseHelper(getContext());
        artistService = new ArtistService(databaseHelper);

        loadArtistsFromDatabase();

        return view;
    }

    private void loadArtistsFromDatabase() {
        List<ArtistModel> artists = artistService.list();
        artistsList.clear();
        artistsList.addAll(artists);
        artistAdapter.notifyDataSetChanged();
    }

    public void updateArtists() {
        loadArtistsFromDatabase();
    }

    public List<ArtistModel> getArtistsList() {
        return artistsList;
    }
}
