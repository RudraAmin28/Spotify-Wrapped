package com.example.spotifywrapped.firestore;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;


public class FireStoreActivity extends Activity {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference usersCollectionRef = db.collection("users");

    static String uid = FirebaseAuth.getInstance().getCurrentUser() != null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "DEFAULT";


    public static void saveSpotifyWrap(SpotifyWrapData finalSpotifyData) {
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
        String currentDate = month + "-" + dayOfMonth + "-" + year;
        singleWrapped.put("Date", currentDate);





        usersCollectionRef.document(uid).collection("data")
                .add(singleWrapped)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }



    public static void fetchSpotifyWraps() {
        CollectionReference dataCollectionRef = usersCollectionRef.document(uid).collection("data");

        dataCollectionRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}
