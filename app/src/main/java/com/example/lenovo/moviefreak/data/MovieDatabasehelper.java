package com.example.lenovo.moviefreak.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Lenovo on 11-05-2018.
 */

public class MovieDatabasehelper extends SQLiteOpenHelper {

    public static final String AUTHORITY = "com.example.lenovo.moviefreak";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String path_Tasks = "MovieDetails";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(path_Tasks).build();
    public static final String Databasename = "MovieDatabase";
    public static final String tablename = "MovieDetails";
    public static final int version = 1;
    public static final String id = "id";
    public static final String movieid = "movieid";
    public static final String movieoriginaltitle = "movieoriginaltitle";
    public static final String moviebackdroppath = "backdroppath";
    public static final String movieposterpath = "posterpath";
    public static final String movierating = "rating";
    public static final String moviereleasedate = "releasedate";
    public static final String movieoverview = "overview";
    public Context c;

    public MovieDatabasehelper(Context context) {
        super(context, Databasename, null, version);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String query = "CREATE TABLE " + tablename + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + movieid + " INTEGER, " + movieoriginaltitle + " TEXT," +
                moviebackdroppath + " TEXT," + movieposterpath + " TEXT," + movierating + " INTEGER," + moviereleasedate + " TEXT," +
                movieoverview + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        onCreate(db);
    }
}


