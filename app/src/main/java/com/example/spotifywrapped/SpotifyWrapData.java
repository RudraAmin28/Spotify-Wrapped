package com.example.spotifywrapped;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpotifyWrapData {
    public SpotifyArtist artistData;
    public SpotifyTrack trackData;
    public String date;
    public String timeSpan;

    private static String mAccessToken, mAccessCode;
    private Call mCall;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    public static void setToken(String token) {
        mAccessToken = token;
    }

    public void onGetArtistData(int timeRange, final Runnable callback) {
        if (mAccessToken == null) {
            System.out.println("unfortunate1");
//            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        final Request request;
        switch (timeRange) {
            case 1:
                request = new Request.Builder()
                        .url("https://api.spotify.com/v1/me/top/artists?time_range=short_term")
                        .addHeader("Authorization", "Bearer " + mAccessToken)
                        .build();
                timeSpan = "1 Month";
                break;
            case 2:
                request = new Request.Builder()
                        .url("https://api.spotify.com/v1/me/top/artists?time_range=medium_term")
                        .addHeader("Authorization", "Bearer " + mAccessToken)
                        .build();
                timeSpan = "6 Months";
                break;
            case 3:
                request = new Request.Builder()
                        .url("https://api.spotify.com/v1/me/top/artists?time_range=long_term")
                        .addHeader("Authorization", "Bearer " + mAccessToken)
                        .build();
                timeSpan = "1 Year";
                break;
            default:
                request = null;
                break;
        }

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
//                Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray items = jsonObject.getJSONArray("items");

                    //TOP 5 ARTISTS
                    ArrayList<String> topFiveArtists = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        topFiveArtists.add(items.getJSONObject(i).getString("name"));
                    }

                    //TOP ARTIST IMAGE URL
                    String topArtistImageString = items.getJSONObject(0).getJSONArray("images").getJSONObject(1).getString("url");



                    HashMap<String, Integer> genreCounts = new HashMap<>();

                    for (int i = 0; i < items.length(); i++) {
                        JSONArray genres = items.getJSONObject(i).getJSONArray("genres");
                        for (int j = 0; j < genres.length(); j++) {
                            genreCounts.put(genres.getString(j), genreCounts.getOrDefault(genres.getString(j), 0) + 1);
                        }
                    }


                    ArrayList<ArrayList<String>> buckets = new ArrayList<>();
                    for (int i = 0; i < 100; i++) {
                        buckets.add(new ArrayList<>());
                    }

                    for (String key : genreCounts.keySet()) {
                        int count = genreCounts.get(key);
                        buckets.get(count).add(key);
                    }

                    //TOP 5 ALBUMS and the link to image of the first one
                    ArrayList<String> topGenres = new ArrayList<>();
//                    String FirstAlbumImage = "";

                    int currInd = 99;
                    while (currInd >= 0 && topGenres.size() != 5) {
                        if (buckets.get(currInd) != null) {
                            topGenres.addAll(buckets.get(currInd));
                        }
                        currInd--;
                    }
                    topGenres = new ArrayList<>(topGenres.subList(0, Math.min(5, topGenres.size())));

                    for (int i = 0; i < 5; i++) {
                        StringBuilder titleCase = new StringBuilder(topGenres.get(i).length());
                        boolean nextTitleCase = true;

                        for (char c : topGenres.get(i).toCharArray()) {
                            if (Character.isSpaceChar(c)) {
                                nextTitleCase = true;
                            } else if (nextTitleCase) {
                                c = Character.toTitleCase(c);
                                nextTitleCase = false;
                            }

                            titleCase.append(c);
                        }

                        topGenres.set(i, titleCase.toString());
                    }

                    artistData = new SpotifyArtist(topFiveArtists, topArtistImageString, topGenres);

//                    setTextAsync(topArtistImageString, profileTextView);
                    callback.run();
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
//                    Toast.makeText(MainActivity.this, "Failed to parse data, watch Logcat for more details",
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onGetAlbumData(int timeRange, final Runnable callback) {
        if (mAccessToken == null) {
//            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        final Request request;
        switch (timeRange) {
            case 1:
                request = new Request.Builder()
                        .url("https://api.spotify.com/v1/me/top/tracks?time_range=short_term")
                        .addHeader("Authorization", "Bearer " + mAccessToken)
                        .build();
                break;
            case 2:
                request = new Request.Builder()
                        .url("https://api.spotify.com/v1/me/top/tracks?time_range=medium_term")
                        .addHeader("Authorization", "Bearer " + mAccessToken)
                        .build();
                break;
            case 3:
                request = new Request.Builder()
                        .url("https://api.spotify.com/v1/me/top/tracks?time_range=long_term")
                        .addHeader("Authorization", "Bearer " + mAccessToken)
                        .build();
                break;
            default:
                request = null;
                break;
        }

        cancelCall();
        mCall = mOkHttpClient.newCall(request);
//        System.out.println(mCall);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
//                Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("Response: " + response);
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray items = jsonObject.getJSONArray("items");


                    //TOP 5 TRACKS and image link of the first one
                    ArrayList<String> topTracks = new ArrayList<>();
                    String firstTrackImage = items.getJSONObject(0).getJSONObject("album").getJSONArray("images").getJSONObject(1).getString("url");

                    for (int i = 0; i < 5; i++) {
                        topTracks.add(items.getJSONObject(i).getString("name"));
                    }

                    ArrayList<String> topTrackURLs = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        topTrackURLs.add(items.getJSONObject(i).getString("uri"));
                    }

//                    System.out.println("Toptracks: " + topTracks.get(0));

                    HashMap<String, Integer> albumCounts = new HashMap<>();

                    for (int i = 0; i < items.length(); i++) {
                        String albumName = items.getJSONObject(i).getJSONObject("album").getString("name");
                        albumCounts.put(albumName, albumCounts.getOrDefault(items.getJSONObject(i).getJSONObject("album").getString("name"), 0) + 1);
                    }

                    ArrayList<ArrayList<String>> buckets = new ArrayList<>();
                    for (int i = 0; i < 50; i++) {
                        buckets.add(new ArrayList<>());
                    }

                    for (String key : albumCounts.keySet()) {
                        int count = albumCounts.get(key);
                        buckets.get(count).add(key);
                    }

                    //TOP 5 ALBUMS and the link to image of the first one
                    ArrayList<String> topAlbums = new ArrayList<>();
                    String firstAlbumImage = "";

                    int currInd = 49;
                    while (currInd >= 0 && topAlbums.size() != 5) {
                        if (buckets.get(currInd) != null) {
                            topAlbums.addAll(buckets.get(currInd));
                        }
                        currInd--;
                    }

                    for (int i = 0; i < items.length(); i++) {
                        String albumName = items.getJSONObject(i).getJSONObject("album").getString("name");
                        if (albumName.equals(topAlbums.get(0))) {
                            firstAlbumImage = items.getJSONObject(i).getJSONObject("album").getJSONArray("images").getJSONObject(1).getString("url");
                        }
                    }

                    topAlbums = new ArrayList<>(topAlbums.subList(0, Math.min(5, topAlbums.size())));

                    for (int i = 0; i < topAlbums.size(); i++) {
                        System.out.println(topAlbums.get(i));
                    }
                    trackData = new SpotifyTrack(topTracks, firstTrackImage, topAlbums, firstAlbumImage, topTrackURLs);

//                    setTextAsync(topTracks.get(0), profileTextView);
                    callback.run();
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
//                    Toast.makeText(MainActivity.this, "Failed to parse data, watch Logcat for more details",
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

//    private void setTextAsync(final String text, TextView textView) {
//        runOnUiThread(() -> textView.setText(text));
//    }
}
