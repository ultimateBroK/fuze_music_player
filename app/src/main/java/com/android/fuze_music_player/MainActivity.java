package com.android.fuze_music_player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;


import com.android.fuze_music_player.adapter.ViewPagerAdapter;
import com.android.fuze_music_player.fragment.ArtistsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    Button btnHistory, btnLastAdded, btnShuffle;
    ImageButton imgbtnPlay;
    ImageView imageView;
    TabLayout tabLayout;
    TextView textView2;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHistory = findViewById(R.id.btnHistory);
        btnLastAdded = findViewById(R.id.btnLastAdded);
        btnShuffle = findViewById(R.id.btnShuffle);
        imgbtnPlay = findViewById(R.id.imgbtnPlay);
        imageView = findViewById(R.id.imageView);
        tabLayout = findViewById(R.id.tab_layout);

        private void initViewPager() {
            tabLayout = findViewById(R.id.tab_layout);
            viewPager2 = findViewById(R.id.view_pager);
            viewPagerAdapter = new ViewPagerAdapter(this);
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

            int initialPosition = viewPager2.getCurrentItem();
//            updateHeader(initialPosition);
        }

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myintent = new Intent(MainActivity.this, History_Screen.class);
//                startActivity(myintent);
//                Toast.makeText(MainActivity.this, "History clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnLastAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myintent = new Intent(MainActivity.this, Last_added_Screen.class);
//                startActivity(myintent);
//                Toast.makeText(MainActivity.this, "Last added clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myintent = new Intent(MainActivity.this, activity_music_player.class);
//                startActivity(myintent);
//                Toast.makeText(MainActivity.this, "Shuffle clicked", Toast.LENGTH_SHORT).show();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        Toast.makeText(MainActivity.this, "For You tab selected", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Songs tab selected", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Albums tab selected", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Intent myintent = new Intent(MainActivity.this, ArtistsFragment.class);
                        startActivity(myintent);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
