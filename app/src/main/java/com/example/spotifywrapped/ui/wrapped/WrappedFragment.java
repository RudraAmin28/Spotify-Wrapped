package com.example.spotifywrapped.ui.wrapped;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.MainActivity;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.SpotifyWrapData;
import com.example.spotifywrapped.SpotifyWrappedStoryActivity;
import com.example.spotifywrapped.databinding.FragmentWrappedBinding;
import com.example.spotifywrapped.firestore.FireStoreActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public class WrappedFragment extends Fragment implements WrappedAdapter.OnItemClickListener {
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

        Button generateButton = root.findViewById(R.id.button_generate);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotifyWrapData finalSpotifyData = new SpotifyWrapData();
                finalSpotifyData.onGetArtistData(() -> {
                    finalSpotifyData.onGetAlbumData(() -> {
                        FireStoreActivity.saveSpotifyWrap(finalSpotifyData, () -> {
                            FireStoreActivity.fetchSpotifyWraps(() -> {
                                Intent intent = new Intent(getActivity(), SpotifyWrappedStoryActivity.class);
                                startActivity(intent);
                            });
                        });
                    });
                });
            }
        });


//        SpotifyWrapData data = new SpotifyWrapData();
//        data.artistData = null;
//        data.trackData = null;
//        data.date = "01-01-2024";
//        spotifyWrapDataArrayList.add(data);



        FireStoreActivity.fetchSpotifyWraps(() -> {
            spotifyWrapDataArrayList.clear();
            for (SpotifyWrapData data : FireStoreActivity.spotifyWraps) {
                spotifyWrapDataArrayList.add(data);
            }

            recyclerView = root.findViewById(R.id.dashboard_recycler_view);
            adapter = new WrappedAdapter(spotifyWrapDataArrayList, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        });


        return root;
    }

    public void onItemClick(int position) {
        // Open SpotifyWrappedStoryActivity with appropriate data
        Intent intent = new Intent(getActivity(), SpotifyWrappedStoryActivity.class);
        intent.putExtra("POSITION", position);
        // Pass data to intent if needed
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}