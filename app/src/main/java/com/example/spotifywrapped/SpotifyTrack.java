package com.example.spotifywrapped;

import java.util.ArrayList;
import java.util.List;

public class SpotifyTrack {
    private ArrayList<String> topTracks;
    private String topTrackImage;

    private ArrayList<String> topAlbums;
    private String topAlbumImage = "";

    public SpotifyTrack(ArrayList<String> topTracks, String topTrackImage, ArrayList<String> topAlbums, String topAlbumImage, ArrayList<String> topTrackURLs) {
        this.topTracks = topTracks;
        this.topTrackImage = topTrackImage;
        this.topAlbums = topAlbums;
        this.topAlbumImage = topAlbumImage;
        this.topTrackURLs = topTrackURLs;
    }

    public ArrayList<String> getTopTrackURLs() {
        return topTrackURLs;
    }

    public void setTopTrackURLs(ArrayList<String> topTrackURLs) {
        this.topTrackURLs = topTrackURLs;
    }

    private ArrayList<String> topTrackURLs;
    public ArrayList<String> getTopTracks() {
        return topTracks;
    }

    public void setTopTracks(ArrayList<String> topTracks) {
        this.topTracks = topTracks;
    }

    public String getTopTrackImage() {
        return topTrackImage;
    }

    public void setTopTrackImage(String topTrackImage) {
        this.topTrackImage = topTrackImage;
    }

    public ArrayList<String> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(ArrayList<String> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public String getTopAlbumImage() {
        return topAlbumImage;
    }

    public void setTopAlbumImage(String topAlbumImage) {
        this.topAlbumImage = topAlbumImage;
    }
}
