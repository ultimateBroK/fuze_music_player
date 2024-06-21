package com.android.fuze_music_player.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.fuze_music_player.fragment.ArtistsFragment;
import com.android.fuze_music_player.fragment.ForYouFragment;

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
                return new ForYouFragment();
            case 2:
                return new ForYouFragment();
            case 3:
                return new ArtistsFragment();
            default:
                return new ForYouFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}