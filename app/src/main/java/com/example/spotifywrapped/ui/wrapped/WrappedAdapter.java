package com.example.spotifywrapped.ui.wrapped;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.SpotifyWrapData;

import java.util.ArrayList;

public class WrappedAdapter extends RecyclerView.Adapter<WrappedAdapter.WrappedViewHolder> {
    private ArrayList<SpotifyWrapData> spotifyDataArrayList;

    public WrappedAdapter(ArrayList<SpotifyWrapData> spotifyDataArrayList) {
        this.spotifyDataArrayList = spotifyDataArrayList;
        Log.d("hjkhjkgiu", ""+spotifyDataArrayList.size());
    }

    @NonNull
    @Override
    public WrappedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_card, parent, false);
        return new WrappedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WrappedViewHolder holder, int position) {
        SpotifyWrapData spotifyWrapData = spotifyDataArrayList.get(position);
        holder.bind(spotifyWrapData);
    }

    @Override
    public int getItemCount() {
        return spotifyDataArrayList.size();
    }

    public class WrappedViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTextView;

        public WrappedViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.wrapped_date);
        }

        public void bind(SpotifyWrapData spotifyWrapData) {
            dateTextView.setText(spotifyWrapData.date);
        }
    }
}