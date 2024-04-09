package com.example.spotifywrapped.ui.wrapped;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrapped.databinding.FragmentWrappedBinding;
import com.example.spotifywrapped.ui.pastwrapped.CardFragment;

public class WrappedFragment extends Fragment {

    private FragmentWrappedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WrappedViewModel wrappedViewModel =
                new ViewModelProvider(this).get(WrappedViewModel.class);

        binding = FragmentWrappedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textSlideshow;
//        wrappedViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}