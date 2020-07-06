package com.example.appcheckinbyqrcode.ui.client.adapter.fragmentevenstadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.sqlite.MyDatabaseHelper;
import com.example.appcheckinbyqrcode.ui.admin.model.FavoriteList;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventAdapterGoingOn extends RecyclerView.Adapter<EventAdapterGoingOn.EventList_holder> {

    private MyDatabaseHelper myDatabaseHelper;
    private static final String TAG = "nnn";
    private List<EventListResponse> items = new ArrayList<>();
    private Context context;
    public boolean changgColorButon = true;
    public int idevents = 0;
    public String name = null;
    public String intro = null;
    public String chariman = null;
    public String image = null;
    ArrayList<FavoriteList> favoriteLists;
    FavoriteList favoritemodel;


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
        favoriteLists = new ArrayList<>();
        favoritemodel = new FavoriteList(0, 0, null, null, null, null);
        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventList_holder holder, int position) {
        String urls = url.getUrlimgevent() + items.get(position).getImage();
        int id = Integer.parseInt(items.get(position).getId());

        myDatabaseHelper = new MyDatabaseHelper(context);
        if (myDatabaseHelper.getFavoriteID(id) == 1) {
            changgColorButon = true;
            holder.imageButton1.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        } else {
            changgColorButon = false;
            holder.imageButton1.setBackgroundResource(R.drawable.ic_favorite_border_red_24dp);
        }
        //delete favorite list
        holder.imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(items.get(position).getId());
                idevents = Integer.parseInt(items.get(position).getId());
                name = items.get(position).getName();
                intro = items.get(position).getIntro();
                chariman = items.get(position).getChairman();
                image = items.get(position).getImage();


                //myDatabaseHelper.deleteFavoriteID(id);
                if (changgColorButon) {
                    changgColorButon = false;
                    FavoriteList favoriteList = new FavoriteList(0, idevents, name, intro, chariman, image);
                    myDatabaseHelper.deleteFavoriteID(id);
                    holder.imageButton1.setBackgroundResource(R.drawable.ic_favorite_border_red_24dp);

                    //Log.d(TAG, "Delete Successful: "+v.toString());
                    Toast.makeText(context, "Thêm sự yêu thích thnàh công", Toast.LENGTH_LONG).show();

                } else {
                    FavoriteList favoriteList = new FavoriteList(0, idevents, name, intro, chariman, image);
//                    Log.d(TAG, "onClick1: " + favoriteList.getName());
//                    Log.d(TAG, "onClick1: " + favoriteList.getIdEvent());

                    myDatabaseHelper.insertFavorite(favoriteList);
                    changgColorButon = true;
                    holder.imageButton1.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                    //Log.d(TAG, "Delete Successful: "+v.toString());
                    Toast.makeText(context, "Xoá sự kiện yêu thích thành công", Toast.LENGTH_SHORT).show();


                }
            }
        });
        Picasso.get().load(urls)
                .into(holder.avatar);
//        Picasso.get().load(urls)
//                .into(holder.avatar);
//        Glide.with(context).load(urls).into(holder.avatar);
//        Glide.with(context)
//                .load(urls)
//                .transform(new CenterCrop(),new RoundedCorners(25))
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
        public RelativeLayout cardView;
        public TextView imageButton1;

        public EventList_holder(View view) {
            super(view);
            name = view.findViewById(R.id.eventnameOnGoing);
            intro = view.findViewById(R.id.eventintroOnGoing);
            day = view.findViewById(R.id.eventdayOnGoing);
            time = view.findViewById(R.id.eventtimeOnGoing);
            place = view.findViewById(R.id.eventplaceOnGoing);
            avatar = view.findViewById(R.id.eventavatarOnGoing);
            cardView = view.findViewById(R.id.linnerOnGoing);
            imageButton1 = view.findViewById(R.id.txtImageFavorite1);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    int id = Integer.parseInt(items.get(getAdapterPosition()).getId());
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });


//            imageButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!changgColorButon){
//                        changgColorButon = true;
//                        imageButton.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
//                        Toast.makeText(context, "aaaa" + v.toString(), Toast.LENGTH_SHORT).show();
//                    }else {
//                        changgColorButon = true;
//                        imageButton.setBackgroundResource(R.drawable.ic_favorite_border_red_24dp);
//                        Toast.makeText(context, "aaaa" + v.toString(), Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//            });

        }

        void getData(EventListResponse ex) {
            // Log.d(TAG, "onBindViewHolder: "+ items.get(position).getEventname());
            name.setText(ex.getName());
            intro.setText(ex.getIntro());
            day.setText(ex.getStart_time());
            time.setText(ex.getEnd_time());
            place.setText(ex.getPlace());

        }




    }
}
