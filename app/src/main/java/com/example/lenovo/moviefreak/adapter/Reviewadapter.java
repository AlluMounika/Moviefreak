package com.example.lenovo.moviefreak.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.moviefreak.R;
import com.example.lenovo.moviefreak.model.ReviewPOJO;

import java.util.ArrayList;

/**
 * Created by Lenovo on 18-05-2018.
 */

public class Reviewadapter extends RecyclerView.Adapter<Reviewadapter.MyViewHolder> {
    ArrayList<ReviewPOJO> reviewarray=new ArrayList<>();
    Context c;
    public Reviewadapter(ArrayList<ReviewPOJO> reviewarray,Context c) {
        this.c=c;
        this.reviewarray=reviewarray;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  tv= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewcard,parent,false);
        return new Reviewadapter.MyViewHolder(tv);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.author.setText(reviewarray.get(position).getReviewauthor());
        holder.content.setText(reviewarray.get(position).getReviewcontent());
        holder.url.setText(reviewarray.get(position).getReviewurl());
    }
    @Override
    public int getItemCount() {
        return reviewarray.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView url;
        TextView content;
        public MyViewHolder(View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.action_author);
            url=itemView.findViewById(R.id.action_url);
            content=itemView.findViewById(R.id.action_content);
        }
    }
}
