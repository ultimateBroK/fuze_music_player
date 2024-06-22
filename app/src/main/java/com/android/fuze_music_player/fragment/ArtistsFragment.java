package com.android.fuze_music_player.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.fuze_music_player.R;
import com.android.fuze_music_player.activity.ArtistDetailActivity;
import com.android.fuze_music_player.adapter.ArtistAdapter;
import com.android.fuze_music_player.model.SongModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class ArtistsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;
    private ArrayList<SongModel> songModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Nhận dữ liệu songModels từ arguments nếu có
        if (getArguments() != null) {
            songModels = (ArrayList<SongModel>) getArguments().getSerializable("songs"); // Sử dụng Serializable
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout cho fragment
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.artists_List);
        recyclerView.setHasFixedSize(true);

        // Lấy danh sách các nghệ sĩ độc nhất và sắp xếp theo bảng chữ cái
        ArrayList<String> uniqueArtists = getUniqueArtists(songModels);
        Collections.sort(uniqueArtists, String::compareTo);

        if (uniqueArtists != null && !uniqueArtists.isEmpty()) {
            artistAdapter = new ArtistAdapter(getContext(), uniqueArtists, artist -> {
                // Mở Activity chi tiết nghệ sĩ khi một nghệ sĩ được chọn
                Intent intent = new Intent(getActivity(), ArtistDetailActivity.class);
                intent.putExtra("artist", artist);
                startActivity(intent);
            });
            recyclerView.setAdapter(artistAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cập nhật tiêu đề của tiêu đề chính
        TextView title = getActivity().findViewById(R.id.title);
        title.setText("Artists");
        getActivity().findViewById(R.id.header_layout).setVisibility(View.VISIBLE);
    }

    // Hàm lấy danh sách các nghệ sĩ độc nhất từ danh sách các bài hát và sắp xếp theo bảng chữ cái
    private ArrayList<String> getUniqueArtists(ArrayList<SongModel> songs) {
        Set<String> artistSet = new HashSet<>();
        for (SongModel song : songs) {
            artistSet.add(song.getArtist());
        }
        ArrayList<String> uniqueArtists = new ArrayList<>(artistSet);
        Collections.sort(uniqueArtists); // Sắp xếp theo thứ tự bảng chữ cái
        return uniqueArtists;
    }
}
