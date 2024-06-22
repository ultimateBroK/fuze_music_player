package com.android.fuze_music_player.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.android.fuze_music_player.R;
import com.android.fuze_music_player.adapter.ViewPagerAdapter;
import com.android.fuze_music_player.model.SongModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    public static ArrayList<SongModel> songModels;
    static MediaPlayer mediaPlayer;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageView playingCoverArt;
    private TextView playingSongName, playingArtistName;
    private SongModel lastPlayedSong;
    private SharedPreferences sharedPreferences;
    private TextView headerTitle;
    private int currentPosition = -1;
    private boolean isPlaying = false;

    private final BroadcastReceiver songInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "com.example.music_player.CURRENT_SONG_INFO".equals(intent.getAction())) {
                String songTitle = intent.getStringExtra("songTitle");
                String songArtist = intent.getStringExtra("songArtist");
                String songPath = intent.getStringExtra("songPath");
                isPlaying = intent.getBooleanExtra("isPlaying", false);

                currentPosition = findSongPositionByPath(songPath);
                playingSongName.setText(songTitle);
                playingArtistName.setText(songArtist);

                byte[] image = null;
                try {
                    image = getAlbumArt(songPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (image != null) {
                    Glide.with(MainActivity.this).asBitmap().load(image).into(playingCoverArt);
                } else {
                    playingCoverArt.setImageResource(R.drawable.music_note);
                }
                updatePlayingStatus(isPlaying);
            }
        }
    };

    public static ArrayList<SongModel> getAllAudio(Context context) {
        ArrayList<SongModel> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder)) {
            if (cursor != null) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
                int titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
                int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idColumn);
                    String title = cursor.getString(titleColumn);
                    String artist = cursor.getString(artistColumn);
                    String album = cursor.getString(albumColumn);
                    String duration = cursor.getString(durationColumn);
                    String path = cursor.getString(pathColumn);

                    SongModel songModel = new SongModel(id, title, artist, album, duration, path);
                    tempAudioList.add(songModel);
                }
            }
        } catch (Exception e) {
            Log.e("GetAllAudio", "Error fetching audio files", e);
        }

        return tempAudioList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playingCoverArt = findViewById(R.id.playing_cover_art);
        playingSongName = findViewById(R.id.playing_song_name);
        playingArtistName = findViewById(R.id.playing_artist_name);
        headerTitle = findViewById(R.id.title);

        sharedPreferences = getSharedPreferences("music_player", MODE_PRIVATE);

        if (checkPermission()) {
            try {
                initApp();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        registerReceiver(songInfoReceiver, new IntentFilter("com.example.music_player.CURRENT_SONG_INFO"));

        RelativeLayout playingStatusLayout = findViewById(R.id.playing_status);
        playingStatusLayout.setOnClickListener(v -> {
            if (isPlaying && currentPosition != -1) {
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("position", currentPosition);
                intent.putExtra("songs", songModels);
                intent.putExtra("isFromStatus", true); // Thêm cờ để nhận biết mở từ trạng thái đang phát nhạc
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "No song is currently playing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_up_in, R.anim.no_animation); // Hoạt ảnh vào
    }

    private int findSongPositionByPath(String path) {
        for (int i = 0; i < songModels.size(); i++) {
            if (songModels.get(i).getPath().equals(path)) {
                return i;
            }
        }
        return -1;
    }

    private void updatePlayingStatus(boolean isPlaying) {
        // Cập nhật các phần tử UI hoặc trạng thái liên quan đến trạng thái chơi nhạc
        // Ví dụ: thay đổi biểu tượng nút phát/tạm dừng.
    }

    private void initApp() throws IOException {
        songModels = getAllAudio(this);
        saveSongListToPreferences(songModels);
        initViewPager();
        loadLastPlayedSong();
        updatePlayingStatusBar(lastPlayedSong);
    }

    private void initViewPager() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(this, songModels);
        viewPager2.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.face_24dp);
                    break;
                case 1:
                    tab.setIcon(R.drawable.music_note_24dp);
                    break;
                case 2:
                    tab.setIcon(R.drawable.album_24dp);
                    break;
                case 3:
                    tab.setIcon(R.drawable.artist_24dp);
                    break;
            }
        }).attach();

        viewPager2.setCurrentItem(0);
        updateHeader(0);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateHeader(position);
            }
        });
    }

    private void updateHeader(int position) {
        LinearLayout headerLayout = findViewById(R.id.header_layout);
        switch (position) {
            case 0:
                headerTitle.setText("For You");
                headerLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                headerTitle.setText("Songs");
                headerLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                headerTitle.setText("Albums");
                headerLayout.setVisibility(View.VISIBLE);
                break;
            case 3:
                headerTitle.setText("Artists");
                headerLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_AUDIO)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This permission is needed to access your audio files.")
                        .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_MEDIA_AUDIO}, REQUEST_CODE))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO}, REQUEST_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    initApp();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadLastPlayedSong() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("last_played_song", null);
        lastPlayedSong = gson.fromJson(json, SongModel.class);
    }

    private void updatePlayingStatusBar(SongModel song) throws IOException {
        if (song != null) {
            playingSongName.setText(song.getTitle());
            playingArtistName.setText(song.getArtist());
            byte[] image = getAlbumArt(song.getPath());
            if (image != null) {
                Glide.with(this).asBitmap().load(image).into(playingCoverArt);
            } else {
                playingCoverArt.setImageResource(R.drawable.music_note);
            }
        }
    }

    private void saveSongListToPreferences(ArrayList<SongModel> songList) {
        SharedPreferences sharedPreferences = getSharedPreferences("music_player", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(songList);
        editor.putString("song_list", json);
        editor.apply();
    }

    private byte[] getAlbumArt(String uri) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(songInfoReceiver);
    }
}
