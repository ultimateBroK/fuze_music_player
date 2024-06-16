package com.android.fuze_music_player.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.fuze_music_player.R;
import com.android.fuze_music_player.model.AlbumModel;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private Context context;
    private List<AlbumModel> albums;

    public AlbumAdapter(Context context, List<AlbumModel> albums) {
        this.context = context;
        this.albums = albums;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_items, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        AlbumModel album = albums.get(position);
        holder.albumName.setText(album.getName());
        holder.artistName.setText(album.getArtist()); // Sử dụng thông tin nghệ sĩ từ AlbumModel
        // Tương tự, bạn cũng có thể sử dụng album.getImgUrl() để tải ảnh album từ URL
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {

        ImageView musicImg;
        TextView albumName;
        TextView artistName;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            musicImg = itemView.findViewById(R.id.music_img);
            albumName = itemView.findViewById(R.id.album_name);
            artistName = itemView.findViewById(R.id.artist_name);
        }
    }
}
