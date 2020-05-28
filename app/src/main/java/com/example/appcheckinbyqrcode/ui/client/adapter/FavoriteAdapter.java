package com.example.appcheckinbyqrcode.ui.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.example.appcheckinbyqrcode.ui.client.model.Favorite;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.Favorite_holder>  {
    private static final String TAG = "nnn";
    private List<Favorite> items;
    private Context context;


    public FavoriteAdapter(List<Favorite> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public Favorite_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_event_favorite, parent, false);
        Favorite_holder vholder = new Favorite_holder(v);
        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Favorite_holder holder, int position) {
        String urls = items.get(position).getEventimage_favorite();
        Glide.with(context).load(urls).into(holder.photo);
        holder.name.setText(items.get(position).getEventname_favorite());
        holder.day.setText(items.get(position).getEventday_favorite());
        holder.persion.setText(items.get(position).getEventpersion_favorite());
        holder.check.setText(items.get(position).getEventcheck_favorite());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class Favorite_holder extends RecyclerView.ViewHolder {
        public TextView name, persion, day, check;
        public ImageView photo;

        public Favorite_holder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.eventname_favorite);
            day = view.findViewById(R.id.eventday_favorite);
            persion = view.findViewById(R.id.eventpersonname_favorite);
            check = view.findViewById(R.id.eventcheck_favorite);
            photo = view.findViewById(R.id.eventimage_favorite);
        }
    }
}
