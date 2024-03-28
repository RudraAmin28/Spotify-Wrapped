package com.example.spotifywrapped.ui.pastwrapped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrapped.databinding.FragmentCardBinding;

public class CardFragment extends Fragment {
    private FragmentCardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CardViewModel cardViewModel =
                new ViewModelProvider(this).get(CardViewModel.class);

        binding = FragmentCardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //final TextView textView = binding.currentYear;
        //cardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}