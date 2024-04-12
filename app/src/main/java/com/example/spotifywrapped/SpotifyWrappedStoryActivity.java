package com.example.spotifywrapped;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SpotifyWrappedStoryActivity extends AppCompatActivity {

    private TextView[] wrappedTextViews;
    private ImageView wrappedImage;
    private TextView title;
    private SpotifyWrapData spotifyData = new SpotifyWrapData();
    private int currPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_wrapped_story);

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

        // Set click listeners for next and previous buttons
        Button buttonPrevious = findViewById(R.id.button_prev);
        Button buttonNext = findViewById(R.id.button_next);

        buttonPrevious.setOnClickListener(view -> showPreviousPage());
        buttonNext.setOnClickListener(view -> showNextPage());
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
        ArrayList<String> newData = new ArrayList<>();
        switch (newPageNum) {
            case 1:
                title.setText(R.string.top_5_artists);
                Picasso.get().load(spotifyData.artistData.getTopArtistImageString()).into(wrappedImage);
                newData = spotifyData.artistData.getTopFiveArtists();
                for (int i = 0; i < newData.size(); i++) {
                    wrappedTextViews[i].setText(newData.get(i));
                }
                break;
            case 2:
                title.setText(R.string.top_5_songs);
                Picasso.get().load(spotifyData.trackData.getTopTrackImage()).into(wrappedImage);
                newData = spotifyData.trackData.getTopTracks();
                for (int i = 0; i < newData.size(); i++) {
                    wrappedTextViews[i].setText(newData.get(i));
                }
                break;
            case 3:
                title.setText(R.string.top_5_albums);
                Picasso.get().load(spotifyData.trackData.getTopAlbumImage()).into(wrappedImage);
                newData = spotifyData.trackData.getTopAlbums();
                for (int i = 0; i < newData.size(); i++) {
                    wrappedTextViews[i].setText(newData.get(i));
                }
                break;
            case 4:
                title.setText(R.string.top_5_genres);
                Picasso.get().load(spotifyData.artistData.getTopArtistImageString()).into(wrappedImage);
                newData = spotifyData.artistData.getTopGenres();
                for (int i = 0; i < newData.size(); i++) {
                    wrappedTextViews[i].setText(newData.get(i));
                }
                break;
        }
//        wrappedTextViews[0].setText();
//        wrappedTextViews[1].setText();
//        wrappedTextViews[2].setText();
//        wrappedTextViews[3].setText();
//        wrappedTextViews[4].setText();
    }
}