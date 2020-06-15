package com.example.appcheckinbyqrcode.ui.client.adapter.fragmentevenstadapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class EventAdapterGoingOn extends RecyclerView.Adapter<EventAdapterGoingOn.EventList_holder> {

    private static final String TAG = "nnn";
    private List<EventListResponse> items= new ArrayList<>();
    private Context context;


    public EventAdapterGoingOn(List<EventListResponse> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public EventList_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_event_goingon_client, parent, false);
        EventList_holder vholder = new EventList_holder(v);
        return  vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventList_holder holder, int position) {
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
        public ImageButton imageButton;

        public EventList_holder(View view) {
            super(view);
            name = view.findViewById(R.id.eventnameOnGoing);
            intro = view.findViewById(R.id.eventintroOnGoing);
            day = view.findViewById(R.id.eventdayOnGoing);
            time = view.findViewById(R.id.eventtimeOnGoing);
            place = view.findViewById(R.id.eventplaceOnGoing);
            avatar = view.findViewById(R.id.eventavatarOnGoing);
            cardView = view.findViewById(R.id.linnerOnGoing);
            imageButton = view.findViewById(R.id.btnImageFavorite);
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
