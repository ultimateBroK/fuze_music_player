package com.android.fuze_music_player.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

//import com.android.fuze_music_player.fragment.ForYouFragment;
//import com.android.fuze_music_player.fragment.SongsFragment;
//import com.android.fuze_music_player.fragment.AlbumsFragment;
import com.android.fuze_music_player.fragment.ArtistsFragment;
import com.android.fuze_music_player.model.SongModel;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {
//    private ArrayList<SongModel> songModels;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
//        this.songModels = songModels;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
//                return new ForYouFragment();
            case 1:
//                return SongsFragment.newInstance(songModels);
            case 2:
//                return AlbumsFragment.newInstance(songModels);
            case 3:
                return new ArtistsFragment();
            default:
                return new ArtistsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
