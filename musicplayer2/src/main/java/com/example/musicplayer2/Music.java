package com.example.musicplayer2;

import android.graphics.Bitmap;

public class Music {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Bitmap getImage_url() {
        return thumb;
    }

    public void setImage_url(Bitmap thumb) {
        this.thumb = thumb;
    }

    int id;//音乐id
    String title;//音乐名称
    String artist;//歌手名
    String url;//音乐路径
    int duration;//音乐时长
    Bitmap thumb;//专辑图片


    public Music(int id, String title, String artist, String url, int duration, Bitmap thumb) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.url = url;
        this.duration = duration;
        this.thumb = thumb;
    }
}
