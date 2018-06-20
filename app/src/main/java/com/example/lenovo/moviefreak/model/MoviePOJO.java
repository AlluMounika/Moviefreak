package com.example.lenovo.moviefreak.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lenovo on 03-05-2018.
 */
public class MoviePOJO implements Parcelable{
    private int id;
    private double vote_average;
    private String title;
    private String poster_path, original_title, backdrop_path, overview, releasedate;

    public MoviePOJO(Parcel in) {
        id = in.readInt();
        vote_average = in.readDouble();
        title = in.readString();
        poster_path = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        releasedate = in.readString();
    }

    public static final Creator<MoviePOJO> CREATOR = new Creator<MoviePOJO>() {
        @Override
        public MoviePOJO createFromParcel(Parcel in) {
            return new MoviePOJO(in);
        }

        @Override
        public MoviePOJO[] newArray(int size) {
            return new MoviePOJO[size];
        }
    };

    public MoviePOJO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(vote_average);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(original_title);
        dest.writeString(backdrop_path);
        dest.writeString(overview);
        dest.writeString(releasedate);
    }
}
