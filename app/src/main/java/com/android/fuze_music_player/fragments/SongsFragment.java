package com.android.fuze_music_player.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.fuze_music_player.R;
import com.android.fuze_music_player.adapter.SongAdapter;
import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.model.SongModel;
import com.android.fuze_music_player.service.ISongService;
import com.android.fuze_music_player.service.SongService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<SongModel> songsOnDevice = getAllAudio(this.getContext());

        // Use a set for faster lookup
        Set<String> dbSongPaths = new HashSet<>();
        for (SongModel dbSong : songs) {
            dbSongPaths.add(dbSong.getPath());
        }

        List<SongModel> newSongsToAdd = new ArrayList<>();

        // Check for new songs and add them to the database if they don't exist
        for (SongModel song : songsOnDevice) {
            if (!dbSongPaths.contains(song.getPath())) {
                newSongsToAdd.add(song);
                dbSongPaths.add(song.getPath());
            }
        }

        // Insert new songs into the database
        for (SongModel newSong : newSongsToAdd) {
            songService.addSong(newSong);
        }

        // Refresh the song list
        songs = songService.list();
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
//        if (artistsFragment instanceof ArtistsFragment) {
//            ((ArtistsFragment) artistsFragment).updateArtists();
//        }
    }

    // Method to open file picker
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        pickSongsLauncher.launch(intent);
    }

    public static ArrayList<SongModel> getAllAudio(Context context) {
        ArrayList<SongModel> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder)) {
            if (cursor != null) {
                int titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
                int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

                while (cursor.moveToNext()) {
                    String title = cursor.getString(titleColumn);
                    String artist = cursor.getString(artistColumn);
                    String album = cursor.getString(albumColumn);
                    String duration = cursor.getString(durationColumn);
                    String path = cursor.getString(pathColumn);

                    SongModel songModel = new SongModel();
                    songModel.setTitle(title);
                    songModel.setArtist(artist);
                    songModel.setAlbum(album);
                    songModel.setDuration(Long.parseLong(duration));
                    songModel.setPath(path);
                    tempAudioList.add(songModel);
                }
            }
        } catch (Exception e) {
            Log.e("GetAllAudio", "Error fetching audio files", e);
        }

        return tempAudioList;
    }

}
