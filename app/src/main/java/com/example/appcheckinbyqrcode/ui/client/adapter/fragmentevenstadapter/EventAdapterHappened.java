package com.example.appcheckinbyqrcode.ui.client.adapter.fragmentevenstadapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class EventAdapterHappened extends RecyclerView.Adapter<EventAdapterHappened.EventList_holder> {

    private static final String TAG = "nnn";
    private List<EventListResponse> items= new ArrayList<>();
    private Context context;


    public EventAdapterHappened(List<EventListResponse> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public EventAdapterHappened.EventList_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_event_happened_client, parent, false);
        EventAdapterHappened.EventList_holder vholder = new EventAdapterHappened.EventList_holder(v);
        return  vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapterHappened.EventList_holder holder, int position) {
        String urls = url.getUrlimgevent() + items.get(position).getImage();
        Glide.with(context).load(urls).into(holder.avatar);
//        Glide.with(context)
//                .load(urls)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.avatar);
//        Picasso.get().load(urls).into(holder.avatar);

        holder.getData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class EventList_holder extends RecyclerView.ViewHolder {// implements View.OnClickListener

        public TextView name, intro, day, time, place;
        public ImageView avatar;
        public CardView cardView;

        public EventList_holder(View view) {
            super(view);
            name = view.findViewById(R.id.eventnameHappened);
            intro = view.findViewById(R.id.eventintroHappened);
            day = view.findViewById(R.id.eventdayHappened);
            time = view.findViewById(R.id.eventtimeHappened);
            place = view.findViewById(R.id.eventplaceHappened);
            avatar = view.findViewById(R.id.eventavatarHappened);
            cardView = view.findViewById(R.id.linnerHappened);
            cardView.setOnClickListener(new View.OnClickListener() {
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
            Log.d("nnn", "tuoi: " + ex.getName());
            // Log.d(TAG, "onBindViewHolder: "+ items.get(position).getEventname());
            name.setText(ex.getName());
            intro.setText(ex.getIntro());
            day.setText(ex.getStart_time());
            time.setText(ex.getEnd_time());
            place.setText(ex.getPlace());
        }
    }
}
