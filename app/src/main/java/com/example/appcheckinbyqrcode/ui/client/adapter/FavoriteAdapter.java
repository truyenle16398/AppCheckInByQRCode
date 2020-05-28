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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.example.appcheckinbyqrcode.ui.client.model.Event;
import com.example.appcheckinbyqrcode.ui.client.model.Favorite;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private static final String TAG = "nnn";
    private List<Favorite> data;
    private Context context;


    public FavoriteAdapter(List<Favorite> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_favorite,null,false);
        FavoriteAdapter.ViewHolder viewHolder = new FavoriteAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
//        String urls = data.get(position).getEventimage_favorite();
//        Log.d(TAG, "onBindViewHolder:  aaaaaaaaaaa "+ urls);
//        Glide.with(context).load(urls).into(holder.photo);
//        Picasso.get()
//                .load(urls)
//                .placeholder(R.drawable.background)
//                .error(R.drawable.loadingkhoa)
//                .into(holder.photo);
        holder.name.setText(data.get(position).getEventname_favorite());
//        holder.day.setText(data.get(position).getEventday_favorite());
//        holder.persion.setText(data.get(position).getEventpersion_favorite());
//        holder.check.setText(data.get(position).getEventcheck_favorite());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{// implements View.OnClickListener

        public TextView name, persion, day, check;
        public ImageView photo;
        public LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.eventname_favorite);
//            day = view.findViewById(R.id.eventday_favorite);
//            persion = view.findViewById(R.id.eventpersonname_favorite);
//            check = view.findViewById(R.id.eventcheck_favorite);
//            photo = view.findViewById(R.id.eventimage_favorite);
//            linearLayout = view.findViewById(R.id.linner_favorite);
//            linearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "onClick: "+ getAdapterPosition());
//                    Toast.makeText(context, ""+getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, EventDetailActivity.class);
//                    context.startActivity(intent);
//                }
//            });

        }
    }
}
