package com.android.fuze_music_player;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.android.fuze_music_player.fragments.AlbumsFragment;
import com.android.fuze_music_player.fragments.ForYouFragment;
import com.android.fuze_music_player.fragments.SongsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ForYouFragment();
            case 1:
                return new SongsFragment();
            case 2:
                return new AlbumsFragment();
            case 3:
//                return new ArtistsFragment();
            default:
                return new ForYouFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}