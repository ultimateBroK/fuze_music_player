package com.android.fuze_music_player.fragment;

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
import com.android.fuze_music_player.model.SongModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArtistsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;
    private ArrayList<SongModel> songModels = new ArrayList<>();

    public ArtistsFragment() {
        // Required empty public constructor
    }

    public static ArtistsFragment newInstance() {
        return new ArtistsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.artists_List);
        recyclerView.setHasFixedSize(true);

        // Load your song data here
        // songModels = loadSongData();

        // Get unique artists
        ArrayList<String> uniqueArtists = getUniqueArtists(songModels);

        if (uniqueArtists != null && !uniqueArtists.isEmpty()) {
            artistAdapter = new ArtistAdapter(getContext(), uniqueArtists, artist -> {
                // Handle artist item click
            });
            recyclerView.setAdapter(artistAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
    }

    private ArrayList<String> getUniqueArtists(ArrayList<SongModel> songs) {
        Set<String> artistSet = new HashSet<>();
        for (SongModel song : songs) {
            artistSet.add(song.getArtist());
        }
        return new ArrayList<>(artistSet);
    }
}