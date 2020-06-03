package com.example.appcheckinbyqrcode.ui.client.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.example.appcheckinbyqrcode.ui.client.model.Event;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventList_holder> {

    private static final String TAG = "nnn";
    private List<EventListResponse> items;
    private Context context;


    public EventAdapter(List<EventListResponse> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public EventList_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_event_client, parent, false);
        EventList_holder vholder = new EventList_holder(v);
        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventList_holder holder, int position) {
        String urls = "http://10.0.2.239:8888/sdc_event/public/"+ items.get(position).getAvatar();
        Glide.with(context).load(urls).into(holder.avatar);
        // Log.d(TAG, "onBindViewHolder: "+ items.get(position).getEventname());
        holder.name.setText(items.get(position).getName());
        holder.intro.setText(items.get(position).getIntro());
        holder.day.setText(items.get(position).getStart_time());
        holder.time.setText(items.get(position).getEnd_time());
        holder.place.setText(items.get(position).getPlace());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class EventList_holder extends RecyclerView.ViewHolder {// implements View.OnClickListener

        public TextView name, intro, day, time, place;
        public ImageView avatar;
        public LinearLayout linearLayout;

        public EventList_holder(View view) {
            super(view);
            name = view.findViewById(R.id.eventname);
            intro = view.findViewById(R.id.eventintro);
            day = view.findViewById(R.id.eventday);
            time = view.findViewById(R.id.eventtime);
            place = view.findViewById(R.id.eventplace);
            avatar = view.findViewById(R.id.eventavatar);
            linearLayout = view.findViewById(R.id.linner);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    int id = Integer.parseInt(items.get(getAdapterPosition()).getId());
                    intent.putExtra("id",id);
                    context.startActivity(intent);
                }
            });

        }
    }
}
