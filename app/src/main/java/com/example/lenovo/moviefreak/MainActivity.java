package com.example.lenovo.moviefreak;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.moviefreak.adapter.MyAdapter;
import com.example.lenovo.moviefreak.data.MovieDatabasehelper;
import com.example.lenovo.moviefreak.model.MoviePOJO;
import com.example.lenovo.moviefreak.utils.Networkhelper;

import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static ImageView imageView;
    public static RecyclerView rv;
   static boolean isfavorite=false;
    URL url;

    public String state="/movie/popular";
    ArrayList<MoviePOJO> arrayList = new ArrayList<>();
    public static final String ONSAVEBUNDLE="bundle";
    public static String movie="2";
    public static final int PopularLOADER_ID=26;
    public static final int TopRatedLOADER_ID=22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.movie_image);
        rv = (RecyclerView) findViewById(R.id.recycler);
        isfavorite=false;
        if(deviseonline())
        {
            if(savedInstanceState!=null)
            {
                if(savedInstanceState.containsKey(ONSAVEBUNDLE))
                {
                    switch (savedInstanceState.getString(ONSAVEBUNDLE))
                    {
                        case "/movie/popular":
                            state="/movie/popular";
                            itempresent(state);
                            break;
                        case "/movie/top_rated":
                            state="/movie/top_rated";
                              itempresent(state);
                            break;
                        case "favorites":
                            state="favorites";
                            itempresent(state);
                    }
                }
                else {
                    itempresent(state);
                }
            }
            else
            {
                itempresent(state);
            }
        }
        else
        {
            if(savedInstanceState!=null)
            {
                if(savedInstanceState.containsKey(ONSAVEBUNDLE)) {
                    switch (savedInstanceState.getString(ONSAVEBUNDLE)) {
                        case "favorites":
                            state = "favorites";
                            itempresent("favorites");
                        default:
                            new AlertDialog.Builder(this).setTitle("No Internet").setMessage("Watch out internet connection").setIcon(R.mipmap.ic_launcher).setNeutralButton("Close", null).show();
                    }
                }
                else {
                        new AlertDialog.Builder(this).setTitle("No Internet").setMessage("Watch out internet connection").setIcon(R.mipmap.ic_launcher).setNeutralButton("Close", null).show();
                }
            }
            else {
                    new AlertDialog.Builder(this).setTitle("No Internet").setMessage("Watch out internet connection").setIcon(R.mipmap.ic_launcher).setNeutralButton("Close", null).show();
            }

        }

        }

    public void itempresent(String val)
    {
        if(val=="/movie/popular")
        {
            Bundle bundle=new Bundle();
            bundle.putString(movie,getString(R.string.movie_popular));
            getSupportLoaderManager().restartLoader(PopularLOADER_ID,bundle,this);
            onSaveInstanceState(bundle);
            isfavorite=false;
        }else if(val=="/movie/top_rated")
        {
            Bundle bundle1=new Bundle();
            bundle1.putString(movie,getString(R.string.movie_toprated));
            getSupportLoaderManager().restartLoader(TopRatedLOADER_ID,bundle1,this);
            isfavorite=false;
        }
        else if(val=="favorites"){
            ArrayList<MoviePOJO> favarray=new ArrayList<>();
            state="favorites";
            MovieDatabasehelper mov=new MovieDatabasehelper(this);
           favarray=showFavoriteMovies();
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                MainActivity.rv.setLayoutManager(new GridLayoutManager(this, 2));
            }
            else{
                MainActivity.rv.setLayoutManager(new GridLayoutManager(this ,2));
            }
            isfavorite=true;
            MainActivity.rv.setAdapter(new MyAdapter(this,favarray));

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ONSAVEBUNDLE,state);

    }
    public boolean deviseonline()
    {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }

        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public void onBackPressed()
    {
        if(isfavorite==true) {
            Bundle bundle = new Bundle();
            bundle.putString(movie, getResources().getString(R.string.movie_popular));
            getSupportLoaderManager().restartLoader(PopularLOADER_ID, bundle, this);
           isfavorite=false;
        }
        else {
            finish();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_favorites) {
            state="favorites";
            itempresent(state);
            return true;
        } else if (itemThatWasClickedId == R.id.action_toprated) {
            if(deviseonline()) {
                state = "/movie/top_rated";
                itempresent(state);
                return true;
            }
            else
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle("NO INTERNET");
                dialog.setMessage("Watch out internet connection" );
                dialog.setPositiveButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).show();
            }
        }else if (itemThatWasClickedId == R.id.action_popular) {
            if(deviseonline()) {
                state = "/movie/popular";
                itempresent(state);
                return true;
            }else
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle("NO INTERNET");
                dialog.setMessage("Watch out internet connection" );
                dialog.setPositiveButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        switch(id){
            case PopularLOADER_ID:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    public String loadInBackground() {
                        url= Networkhelper.urlformation(getString(R.string.movie_popular));
                        try {
                            String res=Networkhelper.httpconnection(url);
                            arrayList =Networkhelper.jsonresponse(res);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
            case TopRatedLOADER_ID:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    public String loadInBackground() {
                        url=Networkhelper.urlformation(getString(R.string.movie_toprated));
                        try {
                            String res=Networkhelper.httpconnection(url);
                            arrayList = Networkhelper.jsonresponse(res);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
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
        switch (loader.getId()){
            case PopularLOADER_ID:
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    MainActivity.rv.setLayoutManager(new GridLayoutManager(this, 2));
                }
                else{
                    MainActivity.rv.setLayoutManager(new GridLayoutManager(this ,2));
                }
               rv.setAdapter(new MyAdapter(this, arrayList));
               break;
            case TopRatedLOADER_ID:
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    MainActivity.rv.setLayoutManager(new GridLayoutManager(this, 2));
                }
                else{
                    MainActivity.rv.setLayoutManager(new GridLayoutManager(this,2));
                }
                rv.setAdapter(new MyAdapter(this, arrayList));
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
   public ArrayList<MoviePOJO> showFavoriteMovies()
    {
        Cursor moviedetails=getContentResolver().query(MovieDatabasehelper.CONTENT_URI,null,null,null,null);
        ArrayList<MoviePOJO> moviedbinfo=new ArrayList<>();
        if(moviedetails.getCount()>0)
        {
            if(moviedetails.moveToFirst()) {
            do {
                MoviePOJO movieinformation = new MoviePOJO();
                movieinformation.setId(moviedetails.getInt(1));
                movieinformation.setOriginal_title((moviedetails.getString(2)));
                movieinformation.setBackdrop_path(moviedetails.getString(3));
                movieinformation.setPoster_path(moviedetails.getString(4));
                movieinformation.setVote_average(moviedetails.getDouble(5));
                movieinformation.setReleasedate(moviedetails.getString(6));
                movieinformation.setOverview(moviedetails.getString(7));
                moviedbinfo.add(movieinformation);
            } while (moviedetails.moveToNext());
        }
        }
        else
        {
                Toast.makeText(this,"NO FAVORITES ",Toast.LENGTH_LONG).show();
        }
        return moviedbinfo;
    }

    @Override
    protected void onResume() {
        super.onResume();
        itempresent(state);

    }


}
