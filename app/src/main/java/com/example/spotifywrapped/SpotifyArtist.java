package com.example.spotifywrapped;

import java.util.ArrayList;

public class SpotifyArtist {
    private String[] topFiveArtists;
    private String topArtistImageString;
    private ArrayList<String> topGenres;

    public SpotifyArtist(String[] topFiveArtists, String topArtistImageString, ArrayList<String> topGenres) {
        this.topFiveArtists = topFiveArtists;
        this.topArtistImageString = topArtistImageString;
        this.topGenres = topGenres;
    }


    public String[] getTopFiveArtists() {
        return topFiveArtists;
    }

    public void setTopFiveArtists(String[] topFiveArtists) {
        this.topFiveArtists = topFiveArtists;
    }

    public String getTopArtistImageString() {
        return topArtistImageString;
    }

    public void setTopArtistImageString(String topArtistImageString) {
        this.topArtistImageString = topArtistImageString;
    }

    public ArrayList<String> getTopGenres() {
        return topGenres;
    }

    public void setTopGenres(ArrayList<String> topGenres) {
        this.topGenres = topGenres;
    }
}
