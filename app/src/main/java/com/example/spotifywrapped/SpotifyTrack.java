package com.example.spotifywrapped;

import java.util.ArrayList;

public class SpotifyTrack {
    private String[] topTracks;
    private String topTrackImage;

    private ArrayList<String> topAlbums;
    private String topAlbumImage = "";

    public SpotifyTrack(String[] topTracks, String topTrackImage, ArrayList<String> topAlbums, String topAlbumImage) {
        this.topTracks = topTracks;
        this.topTrackImage = topTrackImage;
        this.topAlbums = topAlbums;
        this.topAlbumImage = topAlbumImage;
    }

    public String[] getTopTracks() {
        return topTracks;
    }

    public void setTopTracks(String[] topTracks) {
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
