package com.example.spotifywrapped.firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.spotifywrapped.SpotifyArtist;
import com.example.spotifywrapped.SpotifyTrack;
import com.example.spotifywrapped.SpotifyWrapData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class FireStoreActivity {

    public static ArrayList<SpotifyWrapData> spotifyWraps = new ArrayList<>();

    private static final String TAG = "FireStoreActivity";

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference usersCollectionRef = db.collection("users");

    private static String uid = FirebaseAuth.getInstance().getCurrentUser() != null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "DEFAULT";

    public static void saveSpotifyWrap(SpotifyWrapData finalSpotifyData, final Runnable callback) {
        Map<String, Object> singleWrapped = new HashMap<>();
        singleWrapped.put("Top Tracks", finalSpotifyData.trackData.getTopTracks());
        singleWrapped.put("Top Track Image", finalSpotifyData.trackData.getTopTrackImage());
        singleWrapped.put("Top Albums", finalSpotifyData.trackData.getTopAlbums());
        singleWrapped.put("Top Album Image", finalSpotifyData.trackData.getTopAlbumImage());
        singleWrapped.put("Top Five Artists", finalSpotifyData.artistData.getTopFiveArtists());
        singleWrapped.put("Top Artist Image", finalSpotifyData.artistData.getTopArtistImageString());
        singleWrapped.put("Top Genres", finalSpotifyData.artistData.getTopGenres());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Print or use the current date
        String currentDate = (month < 10 ? "0" + month : month) + "-" +
                (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + year;
        singleWrapped.put("Date", currentDate);

        usersCollectionRef.document(uid).collection("data")
                .add(singleWrapped)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        callback.run();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public static void fetchSpotifyWraps(final Runnable callback) {
        spotifyWraps.clear();
        CollectionReference dataCollectionRef = usersCollectionRef.document(uid).collection("data");

        dataCollectionRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> results = document.getData();

                                SpotifyWrapData curr = new SpotifyWrapData();
                                curr.date = (String) results.get("Date");
                                curr.artistData = new SpotifyArtist((ArrayList<String>) results.get("Top Five Artists"), (String) results.get("Top Artist Image"), (ArrayList<String>) results.get("Top Genres"));
                                curr.trackData = new SpotifyTrack((ArrayList<String>) results.get("Top Tracks"), (String) results.get("Top Track Image"), (ArrayList<String>) results.get("Top Albums"), (String) results.get("Top Album Image"));
                                spotifyWraps.add(curr);
                                // Sort the spotifyWraps by date
                                spotifyWraps.sort(new Comparator<SpotifyWrapData>() {
                                    @Override
                                    public int compare(SpotifyWrapData o1, SpotifyWrapData o2) {
                                        // Assuming date is a String in the format "MM-dd-yyyy"
                                        return o2.date.compareTo(o1.date);
                                    }
                                });

                            }
                            callback.run();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
