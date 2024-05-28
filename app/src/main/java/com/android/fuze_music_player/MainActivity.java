package com.android.fuze_music_player;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.fuze_music_player.database.DatabaseHelper;
import com.android.fuze_music_player.service.SongService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnSelectDir;
    private SongService songService;
    private DatabaseHelper databaseHelper;
    private ActivityResultLauncher<Intent> pickSongsLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        databaseHelper = new DatabaseHelper(this);
        songService = new SongService(databaseHelper);
        setupActivityResultLauncher();
    }

    private void init() {
        this.btnSelectDir = findViewById(R.id.btnSelectDir);
        this.btnSelectDir.setOnClickListener(v -> openFilePicker());
    }

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
                            songService.importAudioFromUris(this, uris);
                        }
                    }
                });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        pickSongsLauncher.launch(intent);
    }
}
