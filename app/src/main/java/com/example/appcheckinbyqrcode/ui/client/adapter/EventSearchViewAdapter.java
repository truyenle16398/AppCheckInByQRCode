package com.example.appcheckinbyqrcode.ui.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.network.response.EventSearchListResponse;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;


import java.util.ArrayList;
import java.util.List;

public class EventSearchViewAdapter extends RecyclerView.Adapter<EventSearchViewAdapter.EventList_holder> {

    private static final String TAG = "nnn";
    private List<EventSearchListResponse> items= new ArrayList<>();
    private Context context;


    public EventSearchViewAdapter(List<EventSearchListResponse> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public EventSearchViewAdapter.EventList_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_search, parent, false);
        EventSearchViewAdapter.EventList_holder vholder = new EventSearchViewAdapter.EventList_holder(v);
        return  vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventSearchViewAdapter.EventList_holder holder, int position) {
//        String urls = url.getUrlimgevent() + items.get(position).getImage();
////        Glide.with(context).load(urls).into(holder.avatar);
//        Glide.with(context)
//                .load(urls)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.avatar);
//        Picasso.get().load(urls).into(holder.avatar);
//
//        holder.getData(items.get(position));

        holder.name.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class EventList_holder extends RecyclerView.ViewHolder {// implements View.OnClickListener

        public TextView name;
        public LinearLayout linerSearch;

        public EventList_holder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            linerSearch = view.findViewById(R.id.linerSearch);
            linerSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    int id = Integer.parseInt(items.get(getAdapterPosition()).getId());
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });

        }
        void getData(EventListResponse ex){
            // Log.d(TAG, "onBindViewHolder: "+ items.get(position).getEventname());
            name.setText(ex.getName());
        }
    }
}

