package com.example.spotifywrapped;

import android.content.Intent;
import android.net.Uri;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotifywrapped.firestore.FireStoreActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.SystemBarStyle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotifywrapped.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {

    public static final String REDIRECT_URI = "spotifyapk://auth";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FireStoreActivity FireStoreActivity = new FireStoreActivity();

    public static final String CLIENT_ID = "af97d706ecbd46009cb779d7cf1e25da";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken, mAccessCode;
    private Call mCall;
    private SpotifyWrapData finalSpotifyData = new SpotifyWrapData();

    private TextView tokenTextView, codeTextView, profileTextView;


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private CardView currentYearCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(

                R.id.nav_login, R.id.nav_wrapped, R.id.nav_settings, R.id.nav_pastwrapped)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        tokenTextView = (TextView) findViewById(R.id.token_text_view);
        codeTextView = (TextView) findViewById(R.id.code_text_view);
        profileTextView = (TextView) findViewById(R.id.response_text_view);

        // Initialize the buttons
//        Button tokenBtn = (Button) findViewById(R.id.token_btn);
//        Button codeBtn = (Button) findViewById(R.id.code_btn);
//        Button profileBtn = (Button) findViewById(R.id.profile_btn);
//        Button profileBtn2 = (Button) findViewById(R.id.profile_btn2);
//        Button createwrapButton2 = (Button) findViewById(R.id.createwrapButton2);

        // Set the click listeners for the buttons

//        tokenBtn.setOnClickListener((v) -> {
//            getToken();
//        });
//
//        codeBtn.setOnClickListener((v) -> {
//            getCode();
//        });
//
//        profileBtn.setOnClickListener((v) -> {
//            onGetAlbumData();
//        });
//        profileBtn2.setOnClickListener((v) -> {
//            onGetArtistData();
//        });
//
//        createwrapButton2.setOnClickListener((v) -> {
//
//            FireStoreActivity.saveSpotifyWrap(finalSpotifyData);
//
//
//            FireStoreActivity.fetchSpotifyWraps();
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(MainActivity.this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    /**
     * Get code from Spotify
     * This method will open the Spotify login activity and get the code
     * What is code?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getCode() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
        AuthorizationClient.openLoginActivity(MainActivity.this, AUTH_CODE_REQUEST_CODE, request);
    }


    /**
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();

        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
        }
    }

    /**
     * Get user profile
     * This method will get the user profile using the token
     */
    public void onGetArtistData() {
        if (mAccessToken == null) {
            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/artists?time_range=long_term")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
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

                    SpotifyArtist finalArtistData = new SpotifyArtist(topFiveArtists, topArtistImageString, topGenres);
                    finalSpotifyData.artistData = finalArtistData;


                    setTextAsync(topArtistImageString, profileTextView);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(MainActivity.this, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void onGetAlbumData() {
        if (mAccessToken == null) {
            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=50")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray items = jsonObject.getJSONArray("items");


                    //TOP 5 TRACKS and image link of the first one
                    ArrayList<String> topTracks = new ArrayList<>();
                    String firstTrackImage = items.getJSONObject(0).getJSONObject("album").getJSONArray("images").getJSONObject(1).getString("url");

                    for (int i = 0; i < 5; i++) {
                        topTracks.add(items.getJSONObject(i).getString("name"));
                    }



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

                    for (int i=0; i < topAlbums.size(); i++) {
                        System.out.println(topAlbums.get(i));
                    }
                    SpotifyTrack finalTrackData = new SpotifyTrack(topTracks, firstTrackImage, topAlbums, firstAlbumImage);
                    finalSpotifyData.trackData = finalTrackData;

                    setTextAsync(topTracks.get(0), profileTextView);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(MainActivity.this, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Creates a UI thread to update a TextView in the background
     * Reduces UI latency and makes the system perform more consistently
     *
     * @param text the text to set
     * @param textView TextView object to update
     */
    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

    /**
     * Get authentication request
     *
     * @param type the type of the request
     * @return the authentication request
     */
    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-read-email user-top-read" }) // <--- Change the scope of your requested token here
                .setCampaign("Spotify Wrapped")
                .build();
    }

    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }

    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
        // [END get_user_profile]
    }

    public static void updateEmail(FirebaseUser user, String email) {
        // [START update_email]
        assert user != null;
        System.out.println(user);
        System.out.println(user.getEmail());
        System.out.println(email);
        user.verifyBeforeUpdateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });
        System.out.println(user.getEmail());
        // [END update_email]
    }

    public static void updatePassword(FirebaseUser user, String password) {
        // [START update_password]
        assert user != null;
        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
        // [END update_password]
    }

    public void sendEmailVerification() {
        // [START send_email_verification]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
        // [END send_email_verification]
    }

    public static void deleteUser() {
        // [START delete_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
        // [END delete_user]
    }


    public void getSpotifyData() {

    }
}