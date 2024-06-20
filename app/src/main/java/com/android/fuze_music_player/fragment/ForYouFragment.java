package com.android.fuze_music_player.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.fuze_music_player.R;
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
//            Intent intent = new Intent(getActivity(), LastAddedActivity.class);
//            startActivity(intent);
        });

        binding.historyButton.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), HistoryActivity.class);
//            startActivity(intent);
        });

        binding.shuffleButton.setOnClickListener(v -> {
            // Thêm mã để xử lý sự kiện nhấn nút Shuffle tại đây
            // Ví dụ: Trộn và phát nhạc ngẫu nhiên từ danh sách nhạc
            // Hoặc chuyển đến một Activity khác để phát nhạc ngẫu nhiên
        });

        // Hide header layout for ForYouFragment
        if (getActivity() != null) {
            getActivity().findViewById(R.id.header_layout).setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
