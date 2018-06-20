package com.example.lenovo.moviefreak.model;

/**
 * Created by Lenovo on 14-05-2018.
 */

public class MovietrailerPOJO {
    public String id;
    public String key;
    public String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return "https://www.youtube.com/watch?v=" + "" + key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
