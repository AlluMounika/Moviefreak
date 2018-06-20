package com.example.lenovo.moviefreak.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.moviefreak.R;
import com.example.lenovo.moviefreak.model.MovietrailerPOJO;

import java.util.ArrayList;

/**
 * Created by Lenovo on 14-05-2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    ArrayList<MovietrailerPOJO> trailerarray=new ArrayList<>();
    Context c;
    public TrailerAdapter(ArrayList<MovietrailerPOJO> trailerarray1, Context c) {
        this.trailerarray = trailerarray1;
        this.c = c;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tv=null;
        tv= LayoutInflater.from(parent.getContext()).inflate(R.layout.trailerview, parent, false);
        return new TrailerAdapter.MyViewHolder(tv);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.trailer.setText(trailerarray.get(position).getName());
    }
    @Override
    public int getItemCount() {
            return trailerarray.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trailer;
        public MyViewHolder(View itemView) {
            super(itemView);
                trailer = itemView.findViewById(R.id.youbutton);
                trailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int p = getLayoutPosition();
                        Intent viewIntent = new Intent(c.getString(R.string.action), Uri.parse(trailerarray.get(p).getKey()));
                        c.startActivity(viewIntent);
                    }
                });
        }
    }
}
