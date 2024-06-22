    package com.android.fuze_music_player.adapter;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.android.fuze_music_player.R;
    import com.android.fuze_music_player.model.SongModel;

    import java.util.List;

    public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

        private Context context;
        private List<SongModel> songs;

        public SongAdapter(Context context, List<SongModel> songs) {
            this.context = context;
            this.songs = songs;
        }

        @NonNull
        @Override
        public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
            return new SongViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
            SongModel song = songs.get(position);
            holder.songName.setText(song.getTitle());
            holder.artistName.setText(song.getArtist());

            // Load ảnh sử dụng Glide hoặc Picasso nếu có URL ảnh
            // Glide.with(context).load(song.getImgUrl()).into(holder.musicImg);
        }

        @Override
        public int getItemCount() {
            return songs.size();
        }

        public void updateData(List<SongModel> songs) {
            this.songs = songs;
            notifyDataSetChanged();
        }

        public static class SongViewHolder extends RecyclerView.ViewHolder {
            TextView songName;
            TextView artistName;

            public SongViewHolder(@NonNull View itemView) {
                super(itemView);
                songName = itemView.findViewById(R.id.song_name);
                artistName = itemView.findViewById(R.id.artist_name);
            }
        }
    }

