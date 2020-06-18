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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventAdapterGoingOn extends RecyclerView.Adapter<EventAdapterGoingOn.EventList_holder> {

    private static final String TAG = "nnn";
    private List<EventListResponse> items= new ArrayList<>();
    private Context context;
    public  boolean changgColorButon = true;


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
//        Glide.with(context).load(urls).into(holder.avatar);
//        Glide.with(context)
//                .load(urls)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.avatar);
        Picasso.get().load(urls).into(holder.avatar);

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
        public TextView imageButton;

        public EventList_holder(View view) {
            super(view);
            name = view.findViewById(R.id.eventnameOnGoing);
            intro = view.findViewById(R.id.eventintroOnGoing);
            day = view.findViewById(R.id.eventdayOnGoing);
            time = view.findViewById(R.id.eventtimeOnGoing);
            place = view.findViewById(R.id.eventplaceOnGoing);
            avatar = view.findViewById(R.id.eventavatarOnGoing);
            cardView = view.findViewById(R.id.linnerOnGoing);
            imageButton = view.findViewById(R.id.txtImageFavorite);
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    int id = Integer.parseInt(items.get(getAdapterPosition()).getId());
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!changgColorButon){
                        changgColorButon = true;
                        imageButton.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                        Toast.makeText(context, "aaaa" + v.toString(), Toast.LENGTH_SHORT).show();
                    }else {
                        changgColorButon = true;
                        imageButton.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                        Toast.makeText(context, "aaaa" + v.toString(), Toast.LENGTH_SHORT).show();
                    }



                }
            });


        }
        void getData(EventListResponse ex){
            // Log.d(TAG, "onBindViewHolder: "+ items.get(position).getEventname());
            name.setText(ex.getName());
            intro.setText(ex.getIntro());
            day.setText(ex.getStart_time());
            time.setText(ex.getEnd_time());
            place.setText(ex.getPlace());
        }


    }
}
