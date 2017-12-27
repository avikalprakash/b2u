package com.lueinfo.bshop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lueinfo.bshop.R;

import java.util.List;


/**
 * Created by Fujitsu on 27/05/2017.
 */

public class RecycleGalleryAdapter extends RecyclerView.Adapter<RecycleGalleryAdapter.MyViewHolder> {


    private  List<PlainClass> movieItems;
    Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,rating;
        NetworkImageView imag;

        public MyViewHolder(View view) {
            super(view);
            imag = (NetworkImageView) view.findViewById(R.id.thumbnail);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
          // year = (TextView) view.findViewById(R.id.releaseYear);
        }
    }


    public RecycleGalleryAdapter(Context context, List<PlainClass> moviesList) {

        this.context = context;
        this.movieItems = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eeeeventiitem, parent, false);
                 return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PlainClass movie = movieItems.get(position);
        holder.title.setText(movie.getTitle());
       holder.genre.setText(movie.getTimestamp());
        imageLoader.get(movie.getThumbnailUrl(), ImageLoader.getImageListener(holder.imag, R.mipmap.ic_launcher, R.mipmap.ic_launcher));

       // holder.genre.setText(movie.getFname());

       // holder.imag.setImageUrl(movieItems.get(position).getThumbnailUrl(), imageLoader);
       //  holder.rating.setText(""+movie.getRating());
       // Picasso.with(context).load(movieItems.get(position).getThumbnailUrl()).into(holder.imag);

        if(!URLUtil.isValidUrl(movieItems.get(position).getThumbnailUrl()))
        {
            holder.imag.setVisibility(View.GONE);
        }
        else
        {
            holder.imag.setVisibility(View.VISIBLE);//add this
            try {
                holder.imag.setImageUrl(movieItems.get(position).getThumbnailUrl(), imageLoader);
            }catch (Exception e){

                Toast.makeText(context,"On Activity "+e.toString(),Toast.LENGTH_LONG).show();
            }
        }


    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {

        return movieItems.size();
    }


}

