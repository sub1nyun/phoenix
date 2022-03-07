package com.example.test.iot;

public class MusicDTO {
    String music_title, music_uri;

    public MusicDTO(String music_title, String music_uri) {
        this.music_title = music_title;
        this.music_uri = music_uri;
    }

    public String getMusic_title() {
        return music_title;
    }

    public void setMusic_title(String music_title) {
        this.music_title = music_title;
    }

    public String getMusic_uri() {
        return music_uri;
    }

    public void setMusic_uri(String music_uri) {
        this.music_uri = music_uri;
    }
}
