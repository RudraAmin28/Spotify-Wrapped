package com.example.spotifywrapped;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotifywrapped.firestore.FireStoreActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SpotifyWrappedStoryActivity extends AppCompatActivity {

    private TextView[] wrappedTextViews;
    private ImageView wrappedImage;
    private TextView title;
    private int currPage;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_wrapped_story);
        int currPage = 1;
        position = getIntent().getIntExtra("POSITION", -1);

        // Initialize TextViews for artists, songs, and albums
        wrappedTextViews = new TextView[]{
                findViewById(R.id.wrappedStoryTextView1),
                findViewById(R.id.wrappedStoryTextView2),
                findViewById(R.id.wrappedStoryTextView3),
                findViewById(R.id.wrappedStoryTextView4),
                findViewById(R.id.wrappedStoryTextView5)
        };

        wrappedImage = findViewById(R.id.wrappedImageView);
        title = findViewById(R.id.wrappedTitleTextView);

        updatePage(currPage);

        // Set click listeners for next and previous buttons
        Button buttonPrevious = findViewById(R.id.button_prev);
        Button buttonNext = findViewById(R.id.button_next);
        ImageButton btnClose = findViewById(R.id.btnClose);

        buttonPrevious.setOnClickListener(view -> showPreviousPage());
        buttonNext.setOnClickListener(view -> showNextPage());
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity to go back to the previous fragment
                finish();
            }
        });
    }

//    private void hideAllTextViews(TextView[] textViews) {
//        for (TextView textView : textViews) {
//            textView.setVisibility(View.GONE);
//        }
//    }

    private void showPreviousPage() {
        if (currPage > 1) {
            updatePage(--currPage);
        }
    }

    private void showNextPage() {
        if (currPage < 4) {
            updatePage(++currPage);
        }
    }

    private void updatePage(int newPageNum) {
        Button buttonPrevious = findViewById(R.id.button_prev);
        Button buttonNext = findViewById(R.id.button_next);
//        System.out.println(position);
//        int wrapsListSize = FireStoreActivity.spotifyWraps.size();
        ArrayList<String> newData;

        SpotifyWrapData wrapData;
        if (position == -1) {
            wrapData = FireStoreActivity.latest;
        } else {
            wrapData = FireStoreActivity.spotifyWraps.get(position);
        }

        switch (newPageNum) {
            case 1:
                title.setText(R.string.top_5_artists);
                buttonPrevious.setVisibility(View.INVISIBLE);
                Picasso.get().load(wrapData.artistData.getTopArtistImageString()).into(wrappedImage);

                newData = wrapData.artistData.getTopFiveArtists();
                for (int i = 0; i < newData.size(); i++) {
                    wrappedTextViews[i].setText(newData.get(i));
                }
//                System.out.println(wrapData.artistData);
                break;
            case 2:
                title.setText(R.string.top_5_songs);
                buttonPrevious.setVisibility(View.VISIBLE);
                Picasso.get().load(wrapData.trackData.getTopTrackImage()).into(wrappedImage);

                newData = wrapData.trackData.getTopTracks();
                for (int i = 0; i < newData.size(); i++) {
                    wrappedTextViews[i].setText(newData.get(i));
                }
                break;
            case 3:
                title.setText(R.string.top_5_albums);
                buttonNext.setVisibility(View.VISIBLE);
                Picasso.get().load(wrapData.trackData.getTopAlbumImage()).into(wrappedImage);

                newData = wrapData.trackData.getTopAlbums();
                for (int i = 0; i < newData.size(); i++) {
                    wrappedTextViews[i].setText(newData.get(i));
                }
                break;
            case 4:
                title.setText(R.string.top_5_genres);
                buttonNext.setVisibility(View.INVISIBLE);
                Picasso.get().load("https://atlas-content-cdn.pixelsquid.com/stock-images/symbol-music-note-gold-musical-Q99QKV3-600.jpg").into(wrappedImage);

                newData = wrapData.artistData.getTopGenres();
                for (int i = 0; i < newData.size(); i++) {
                    wrappedTextViews[i].setText(newData.get(i));
                }
                break;
        }
    }
}