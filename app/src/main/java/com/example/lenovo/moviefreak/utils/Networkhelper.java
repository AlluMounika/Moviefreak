package com.example.lenovo.moviefreak.utils;

import android.net.Uri;

import com.example.lenovo.moviefreak.BuildConfig;
import com.example.lenovo.moviefreak.model.MoviePOJO;
import com.example.lenovo.moviefreak.model.MovietrailerPOJO;
import com.example.lenovo.moviefreak.model.ReviewPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lenovo on 23-05-2018.
 */

public class Networkhelper {
    public static final String BASE_URI = "http://api.themoviedb.org/3";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w154";
    private static final String API= BuildConfig.APIKEY;
    public static URL urlformation(String s) {
        URL url = null;
        String BASE = BASE_URI + "" + s +""+ "?api_key="+API;
        Uri builtUri = Uri.parse(BASE);
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL imageurl(String s) {
        URL url = null;
        String image_pos="https://image.tmdb.org/t/p/w300"+""+s;
        Uri builtimageUri = Uri.parse(image_pos);
        try {
            url = new URL(builtimageUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    public static URL buildtrailerurl(int id)
    {
        String trailerurl;
        URL url=null;
        String id1=Integer.toString(id);
        trailerurl=BASE_URI+"/movie/"+id1+"/videos?api_key="+API;
        Uri builtUri = Uri.parse(trailerurl);
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static URL buildreviewurl(int id)
    {
        String reviewurl;
        URL url=null;
        String buildid=Integer.toString(id);
        reviewurl =BASE_URI+"/movie/"+buildid+ "/reviews?api_key="+API;
        Uri builtUri = Uri.parse(reviewurl);
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static ArrayList<MovietrailerPOJO> trailersJsondata(String response) throws JSONException {
        ArrayList<MovietrailerPOJO> arrayList1 = new ArrayList<>();
        JSONObject jsonObject;
        jsonObject = new JSONObject(response);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject jsontrailerObject1 = results.getJSONObject(i);
            MovietrailerPOJO mtp = new MovietrailerPOJO();
            mtp.setId(jsontrailerObject1.getString("id"));
            mtp.setKey(jsontrailerObject1.getString("key"));
            mtp.setName(jsontrailerObject1.getString("name"));
            arrayList1.add(mtp);
        }
        return arrayList1;
    }
    public static ArrayList<ReviewPOJO> reviewjsondata(String response) throws JSONException {
        ArrayList<ReviewPOJO> revarraylist=new ArrayList<>();
        JSONObject jsonObject;
        jsonObject = new JSONObject(response);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonreviewObject1 = results.getJSONObject(i);
            ReviewPOJO rev = new ReviewPOJO();
            rev.setReviewauthor(jsonreviewObject1.getString("author"));
            rev.setReviewcontent(jsonreviewObject1.getString("content"));
            rev.setReviewid(jsonreviewObject1.getString("id"));
            rev.setReviewurl(jsonreviewObject1.getString("url"));
            revarraylist.add(rev);
        }
        return revarraylist;
    }

    public static String httpconnection(URL url) throws IOException {
        String res="";
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        InputStream in = urlConn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while(true) {
            String line = reader.readLine();
            if(line == null) {
                break;
            }
            res=res+line;;
        }
        return res;
    }
    public static ArrayList<MoviePOJO> jsonresponse(String res) throws JSONException {
        ArrayList<MoviePOJO> arrayList1 = new ArrayList<>();
        JSONObject jsonObject;
        jsonObject = new JSONObject(res);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonObject1 = results.getJSONObject(i);
            MoviePOJO MP = new MoviePOJO();
            MP.setId(jsonObject1.getInt("id"));
            MP.setVote_average(jsonObject1.getDouble("vote_average"));
            MP.setTitle( jsonObject1.getString("title"));
            MP.setPoster_path(jsonObject1.getString("poster_path"));
            MP.setOriginal_title(jsonObject1.getString("original_title"));
            MP.setBackdrop_path( jsonObject1.getString("backdrop_path"));
            MP.setOverview(jsonObject1.getString("overview"));
            MP.setReleasedate(jsonObject1.getString("release_date"));
            arrayList1.add(MP);
        }
        return arrayList1;
    }
}
