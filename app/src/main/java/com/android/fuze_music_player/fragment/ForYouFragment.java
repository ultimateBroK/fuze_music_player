package com.android.fuze_music_player.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.fuze_music_player.databinding.FragmentForYouBinding;

public class ForYouFragment extends Fragment {

    private FragmentForYouBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentForYouBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up button click listeners
        binding.lastAddedButton.setOnClickListener(v -> {
            // Handle Last Added button click
        });

        binding.historyButton.setOnClickListener(v -> {
            // Handle History button click
        });

        binding.shuffleButton.setOnClickListener(v -> {
            // Handle Shuffle button click
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}