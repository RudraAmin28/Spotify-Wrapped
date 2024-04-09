package com.example.spotifywrapped.ui.pastwrapped;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.databinding.FragmentCardBinding;
import com.example.spotifywrapped.ui.wrapped.WrappedFragment;

public class CardFragment extends Fragment {
    private FragmentCardBinding binding;
    private CardView currentYearCardView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CardViewModel cardViewModel =
                new ViewModelProvider(this).get(CardViewModel.class);
        binding = FragmentCardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.currentYearCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(CardFragment.this).popBackStack();
                findNavController(CardFragment.this).navigate(R.id.nav_wrapped);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}