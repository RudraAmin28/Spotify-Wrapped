package com.example.spotifywrapped.ui.pastwrapped;

<<<<<<< HEAD
=======
import android.app.Activity;
import android.content.Intent;
>>>>>>> origin/Sachi2
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD

import androidx.annotation.NonNull;
=======
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
>>>>>>> origin/Sachi2
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrapped.databinding.FragmentCardBinding;
<<<<<<< HEAD

public class CardFragment extends Fragment {
    private FragmentCardBinding binding;
=======
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.ui.wrapped.WrappedFragment;

public class CardFragment extends Fragment {
    private FragmentCardBinding binding;
    private CardView currentYearCardView;
>>>>>>> origin/Sachi2

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CardViewModel cardViewModel =
                new ViewModelProvider(this).get(CardViewModel.class);

        binding = FragmentCardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
<<<<<<< HEAD
        //final TextView textView = binding.currentYear;
        //cardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
=======

        return root;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.currentYearCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
>>>>>>> origin/Sachi2

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}