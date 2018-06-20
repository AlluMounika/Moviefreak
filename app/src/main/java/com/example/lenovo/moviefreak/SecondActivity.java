package com.example.lenovo.moviefreak;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.moviefreak.adapter.Reviewadapter;
import com.example.lenovo.moviefreak.adapter.TrailerAdapter;
import com.example.lenovo.moviefreak.data.MovieDatabasehelper;
import com.example.lenovo.moviefreak.model.MovietrailerPOJO;
import com.example.lenovo.moviefreak.model.ReviewPOJO;
import com.example.lenovo.moviefreak.utils.Networkhelper;
import com.squareup.picasso.Picasso;
import org.json.JSONException;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    ArrayList<String> moviedetailinfo=new ArrayList<String>();
    public static MovieDatabasehelper mdbh;
    public static RecyclerView rcv,rev;
    double vc;
    public int movieid;
    String response;
    public static final int TrailersLOADER_ID=28;
    public static final int ReviewLOADER_ID=49;
    ArrayList<MovietrailerPOJO> trailersarraylist=new ArrayList<>();
    ArrayList<ReviewPOJO> reviewarraylist=new ArrayList<>();
    @BindView(R.id.secactivitymovie_image) ImageView background_path;
    @BindView(R.id.secactivityposter_path) ImageView poster_path;
    @BindView(R.id.secactivitymovie_name) TextView title;
    @BindView(R.id.secactivityrating) TextView rating;
    @BindView(R.id.secondactivityoverview) TextView overview;
    @BindView(R.id.secactivityrelease_date) TextView releasedate;
    @BindView(R.id.action_favorites) Button favorites;
    @BindView(R.id.movietrailes_tv) TextView trailers_tv;
    @BindView(R.id.moviereview_tv) TextView review_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        moviedetailinfo=getIntent().getStringArrayListExtra(getString(R.string.movie_information));
        vc=getIntent().getDoubleExtra(getString(R.string.vote_average),0);
        display(this,moviedetailinfo.get(0),background_path);
        display(this,moviedetailinfo.get(1),poster_path);
        overview.setText(moviedetailinfo.get(2));
        title.setText(moviedetailinfo.get(3));
        rating.setText(Double.toString(vc));
        releasedate.setText(moviedetailinfo.get(4));
        movieid=getIntent().getIntExtra(getString(R.string.movieid),0);
        Cursor cursor=getContentResolver().query(Uri.parse(MovieDatabasehelper.CONTENT_URI+"/*"),null,MovieDatabasehelper.movieoriginaltitle+" LIKE ?",new String[]{moviedetailinfo.get(3)},null);
         if(cursor.getCount()>=1)
       {
           favorites.setText(R.string.Remove_fav);
       }else
       {
           favorites.setText(R.string.Add_fav);
       }
        mdbh=new MovieDatabasehelper(SecondActivity.this);
        rcv = (RecyclerView) findViewById(R.id.trailer_recycler);
        getSupportLoaderManager().restartLoader(TrailersLOADER_ID,null, SecondActivity.this);
        rev=(RecyclerView)findViewById(R.id.review_recycler);
        getSupportLoaderManager().restartLoader(ReviewLOADER_ID,null, SecondActivity.this);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=getContentResolver().query(Uri.parse(MovieDatabasehelper.CONTENT_URI+"/*"),null,MovieDatabasehelper.movieoriginaltitle+" LIKE ?",new String[]{moviedetailinfo.get(3)},null);
                if(cursor.getCount()>=1)
                {
                    getContentResolver().delete(Uri.parse(MovieDatabasehelper.CONTENT_URI+"/*"),MovieDatabasehelper.movieoriginaltitle + " LIKE ?",new String[]{moviedetailinfo.get(3)});
                    favorites.setText(R.string.Add_fav);

                }
                else
                {
                    favorites.setText(R.string.Remove_fav);
                    ContentValues moviedetailinfovalues=new ContentValues();
                    moviedetailinfovalues.put(MovieDatabasehelper.movieid,movieid);
                    moviedetailinfovalues.put(MovieDatabasehelper.movieoriginaltitle,moviedetailinfo.get(3));
                    moviedetailinfovalues.put(MovieDatabasehelper.moviebackdroppath,moviedetailinfo.get(0));
                    moviedetailinfovalues.put(MovieDatabasehelper.movieposterpath,moviedetailinfo.get(1));
                    moviedetailinfovalues.put(MovieDatabasehelper.movierating,vc);
                    moviedetailinfovalues.put(MovieDatabasehelper.moviereleasedate,moviedetailinfo.get(4));
                    moviedetailinfovalues.put(MovieDatabasehelper.movieoverview,moviedetailinfo.get(2));
                    getContentResolver().insert(Uri.parse(MovieDatabasehelper.CONTENT_URI+""),moviedetailinfovalues);
                }
            }
        });
    }
    public void display(Context c, String s, ImageView image)
    {
        Picasso.with(c).load(Networkhelper.imageurl(s).toString()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.imagenotfound).into(image);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case TrailersLOADER_ID:
                return new AsyncTaskLoader(this) {
                    @Override
                    public Object loadInBackground() {
                        try {
                                URL url1 =Networkhelper. buildtrailerurl(movieid);
                                response = Networkhelper.httpconnection(url1);
                                trailersarraylist =Networkhelper.trailersJsondata(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
            case ReviewLOADER_ID:
                return new AsyncTaskLoader(this) {
                    @Override
                    public Object loadInBackground() {
                        try {
                            URL reviewurl =Networkhelper.buildreviewurl(movieid);
                            response = Networkhelper.httpconnection(reviewurl);
                            reviewarraylist = Networkhelper.reviewjsondata(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        switch (loader.getId()) {
            case TrailersLOADER_ID:
                if(trailersarraylist.isEmpty())
                {
                    trailers_tv.setText(R.string.no_trailers);
                }
                else {
                    trailers_tv.setText(R.string.trailers);
                    SecondActivity.rcv.setLayoutManager(new GridLayoutManager(this, 1));
                    SecondActivity.rcv.setAdapter(new TrailerAdapter(trailersarraylist, this));
                }
                case ReviewLOADER_ID:
                    if(reviewarraylist.isEmpty())
                    {
                        review_tv.setText(R.string.no_reviews);
                    }
                    else {
                        review_tv.setText(R.string.reviews_tv);
                        SecondActivity.rev.setLayoutManager(new GridLayoutManager(this, 1));
                        SecondActivity.rev.setAdapter(new Reviewadapter(reviewarraylist, this));
                    }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}



