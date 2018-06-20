package com.example.lenovo.moviefreak.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.moviefreak.R;
import com.example.lenovo.moviefreak.SecondActivity;
import com.example.lenovo.moviefreak.model.MoviePOJO;
import com.example.lenovo.moviefreak.utils.Networkhelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 29-04-2018.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<MoviePOJO> moviearray;
    Context c;

    public MyAdapter(Context context, ArrayList<MoviePOJO> arrayList) {
        this.moviearray = arrayList;
        this.c = context;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        imagedisplay(holder, position);
    }

    @Override
    public int getItemCount() {
        return moviearray.size();
    }

    public Void imagedisplay(MyAdapter.MyViewHolder holder, int position) {
        Picasso.with(c).load(Networkhelper.imageurl(moviearray.get(position).getPoster_path()).toString()).error(R.mipmap.imagenotfound).into(holder.image_view);
        return null;
    }

    public Void information(View v, int p) {
        ArrayList<String> details = new ArrayList<>();
        details.add(0, moviearray.get(p).getBackdrop_path());
        details.add(1, moviearray.get(p).getPoster_path());
        details.add(2, moviearray.get(p).getOverview());
        details.add(3, moviearray.get(p).getOriginal_title());
        details.add(4, moviearray.get(p).getReleasedate());
        int id = moviearray.get(p).getId();
        double vote_average = moviearray.get(p).getVote_average();
        Intent i = new Intent(c, SecondActivity.class);
        i.putStringArrayListExtra(c.getString(R.string.movie_information), details);
        i.putExtra(c.getString(R.string.movieid), id);
        i.putExtra(c.getString(R.string.vote_average), vote_average);
        v.getContext().startActivity(i);
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            image_view = (ImageView) itemView.findViewById(R.id.movie_image);
            image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getLayoutPosition();
                    information(v, p);
                }
            });
        }
    }
}
