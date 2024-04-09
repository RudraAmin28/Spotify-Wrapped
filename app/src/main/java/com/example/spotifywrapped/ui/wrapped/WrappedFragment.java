package com.example.spotifywrapped.ui.wrapped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.SpotifyWrapData;
import com.example.spotifywrapped.databinding.FragmentWrappedBinding;
import com.example.spotifywrapped.firestore.FireStoreActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WrappedFragment extends Fragment {
    private ArrayList<SpotifyWrapData> spotifyWrapDataArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private WrappedAdapter adapter;

    private FragmentWrappedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WrappedViewModel wrappedViewModel =
                new ViewModelProvider(this).get(WrappedViewModel.class);

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        });

        binding = FragmentWrappedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        SpotifyWrapData data = new SpotifyWrapData();
//        data.artistData = null;
//        data.trackData = null;
//        data.date = "01-01-2024";
//        spotifyWrapDataArrayList.add(data);


        for (SpotifyWrapData data : FireStoreActivity.spotifyWraps) {
            spotifyWrapDataArrayList.add(data);
        }

        recyclerView = root.findViewById(R.id.dashboard_recycler_view);
        adapter = new WrappedAdapter(spotifyWrapDataArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}