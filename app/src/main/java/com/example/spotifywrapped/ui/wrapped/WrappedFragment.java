package com.example.spotifywrapped.ui.wrapped;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.MainActivity;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.SpotifyWrapData;
import com.example.spotifywrapped.SpotifyWrappedStoryActivity;
import com.example.spotifywrapped.databinding.FragmentWrappedBinding;
import com.example.spotifywrapped.firestore.FireStoreActivity;
import com.example.spotifywrapped.ui.login.LoginFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private static OnMusicPlayerListener musicPlayerListener;

    public interface OnMusicPlayerListener {
        void onMusicPlay(String track);
        void onMusicPause(final Runnable callback);
    }

    public static WrappedFragment newInstance(OnMusicPlayerListener listener) {
        WrappedFragment fragment = new WrappedFragment();
        WrappedFragment.setMusicPlayerListener(listener);
        return fragment;
    }

    public static void setMusicPlayerListener(OnMusicPlayerListener listener) {
        musicPlayerListener = listener;
        Log.d(TAG, "Successfully set listener");
    }



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

        Button generateButtonShort = root.findViewById(R.id.button_generate_short);
        Button generateButtonMed = root.findViewById(R.id.button_generate_med);
        Button generateButtonLong = root.findViewById(R.id.button_generate_long);
        generateButtonShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotifyWrapData finalSpotifyData = new SpotifyWrapData();
                finalSpotifyData.onGetArtistData(1, () -> {
                    finalSpotifyData.onGetAlbumData(1, () -> {
                        FireStoreActivity.saveSpotifyWrap(finalSpotifyData, () -> {
                            FireStoreActivity.fetchSpotifyWraps(() -> {
                                adapter.updateData(FireStoreActivity.spotifyWraps);
                                FireStoreActivity.latest = finalSpotifyData;
//                                musicPlayerListener.onMusicPlay(FireStoreActivity.latest.trackData.getTopTrackURLs().get(0));
                                Intent intent = new Intent(getActivity(), SpotifyWrappedStoryActivity.class);
                                startActivity(intent);
                            });
                        });
                    });
                });
            }
        });

        generateButtonMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotifyWrapData finalSpotifyData = new SpotifyWrapData();
                finalSpotifyData.onGetArtistData(2, () -> {
                    finalSpotifyData.onGetAlbumData(2, () -> {
                        FireStoreActivity.saveSpotifyWrap(finalSpotifyData, () -> {
                            FireStoreActivity.fetchSpotifyWraps(() -> {
                                adapter.updateData(FireStoreActivity.spotifyWraps);
                                FireStoreActivity.latest = finalSpotifyData;
//                                musicPlayerListener.onMusicPlay(FireStoreActivity.latest.trackData.getTopTrackURLs().get(0));
                                Intent intent = new Intent(getActivity(), SpotifyWrappedStoryActivity.class);
                                startActivity(intent);
                            });
                        });
                    });
                });
            }
        });

        generateButtonLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotifyWrapData finalSpotifyData = new SpotifyWrapData();
                finalSpotifyData.onGetArtistData(3, () -> {
                    finalSpotifyData.onGetAlbumData(3, () -> {
                        FireStoreActivity.saveSpotifyWrap(finalSpotifyData, () -> {
                            FireStoreActivity.fetchSpotifyWraps(() -> {
                                adapter.updateData(FireStoreActivity.spotifyWraps);
                                FireStoreActivity.latest = finalSpotifyData;
//                                musicPlayerListener.onMusicPlay(FireStoreActivity.latest.trackData.getTopTrackURLs().get(0));
                                Intent intent = new Intent(getActivity(), SpotifyWrappedStoryActivity.class);
                                startActivity(intent);
                            });
                        });
                    });
                });
            }
        });

        FloatingActionButton settingsFab = root.findViewById(R.id.settings_fab);
        settingsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SettingsFragment
                NavHostFragment.findNavController(WrappedFragment.this).navigate(R.id.action_wrappedFragment_to_settingsFragment);
            }
        });


//        SpotifyWrapData data = new SpotifyWrapData();
//        data.artistData = null;
//        data.trackData = null;
//        data.date = "01-01-2024";
//        spotifyWrapDataArrayList.add(data);


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