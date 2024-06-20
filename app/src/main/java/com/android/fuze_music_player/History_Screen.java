package com.android.fuze_music_player;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.fuze_music_player.R;

public class History_Screen extends AppCompatActivity {
    ImageButton imgbtnBack1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_screen);
        imgbtnBack1 = findViewById(R.id.imgbtnBack1);
        imgbtnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(History_Screen.this, "Back clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}