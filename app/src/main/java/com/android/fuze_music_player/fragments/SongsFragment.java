package com.android.fuze_music_player.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.fuze_music_player.R;
import com.android.fuze_music_player.adapter.SongAdapter;
import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.model.SongModel;
import com.android.fuze_music_player.service.AlbumService;
import com.android.fuze_music_player.service.ArtistService;
import com.android.fuze_music_player.service.IArtistService;
import com.android.fuze_music_player.service.ISongService;
import com.android.fuze_music_player.service.SongService;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SongsFragment extends Fragment {
    private ImageButton moreButton;
    private ActivityResultLauncher<Intent> pickSongsLauncher;

    private RecyclerView songsRecyclerView;
    private SongAdapter songAdapter;
    private List<SongModel> songsList = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private ISongService songService;

    public SongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);

        // Initialize UI elements
        moreButton = view.findViewById(R.id.more_button);
        moreButton.setOnClickListener(v -> openFilePicker());

        songsRecyclerView = view.findViewById(R.id.songs_List);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter and set it to RecyclerView
        songAdapter = new SongAdapter(getContext(), songsList);
        songsRecyclerView.setAdapter(songAdapter);

        // Setup database and services
        databaseHelper = new DatabaseHelper(getContext());
        songService = new SongService(databaseHelper);

        // Load songs from database
        loadSongsFromDatabase();

        // Setup activity result launcher
        setupActivityResultLauncher();

        return view;
    }

    // Method to load songs from the database
    private void loadSongsFromDatabase() {
        List<SongModel> songs = songService.list();
        songsList.clear();
        songsList.addAll(songs);
        songAdapter.updateData(songsList);
    }

    // Method to setup activity result launcher
    private void setupActivityResultLauncher() {
        pickSongsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            List<Uri> uris = new ArrayList<>();
                            if (data.getClipData() != null) {
                                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                                    uris.add(data.getClipData().getItemAt(i).getUri());
                                }
                            } else if (data.getData() != null) {
                                uris.add(data.getData());
                            }
                            songService.importAudioFromUris(getContext(), uris);
                            loadSongsFromDatabase(); // Reload songs after import
                            updateAlbumsAndArtists(); // Update albums and artists after import
                        }
                    }
                });
    }

    // Method to update albums and artists after song import
    private void updateAlbumsAndArtists() {
        // Update albums and artists in their respective fragments if available
        Fragment albumsFragment = getParentFragmentManager().findFragmentByTag("AlbumsFragment");
        if (albumsFragment instanceof AlbumsFragment) {
            ((AlbumsFragment) albumsFragment).updateAlbums();
        }

        Fragment artistsFragment = getParentFragmentManager().findFragmentByTag("ArtistsFragment");
        if (artistsFragment instanceof ArtistsFragment) {
            ((ArtistsFragment) artistsFragment).updateArtists();
        }
    }

    // Method to open file picker
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        pickSongsLauncher.launch(intent);
    }
}
