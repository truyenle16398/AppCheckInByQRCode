package com.example.appcheckinbyqrcode.ui.client.adapter.fragmentevenstadapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.appcheckinbyqrcode.sqlite.MyDatabaseHelper;
import com.example.appcheckinbyqrcode.ui.admin.model.FavoriteList;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventAdapterGoingOnHappen extends RecyclerView.Adapter<EventAdapterGoingOnHappen.EventList_holder> {
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

    public EventAdapterGoingOnHappen(List<EventListResponse> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public EventAdapterGoingOnHappen.EventList_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_event_goingon_happen_client, parent, false);
        EventAdapterGoingOnHappen.EventList_holder vholder = new EventAdapterGoingOnHappen.EventList_holder(v);
        favoriteLists = new ArrayList<>();
        favoritemodel = new FavoriteList(0, 0, null, null, null, null);
        return  vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapterGoingOnHappen.EventList_holder holder, int position) {
        String urls = url.getUrlimgevent() + items.get(position).getImage();
        int id = Integer.parseInt(items.get(position).getId());

        myDatabaseHelper = new MyDatabaseHelper(context);
        if (myDatabaseHelper.getFavoriteID(id) == 1) {
            changgColorButon = true;
            holder.imageButton2.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        } else {
            changgColorButon = false;
            holder.imageButton2.setBackgroundResource(R.drawable.ic_favorite_border_red_24dp);
        }
        //delete favorite list
        holder.imageButton2.setOnClickListener(new View.OnClickListener() {
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
                    holder.imageButton2.setBackgroundResource(R.drawable.ic_favorite_border_red_24dp);
                    //Log.d(TAG, "Delete Successful: "+v.toString());
                    Snackbar.make(v, "Xoá sự kiện yêu thích thành công", Snackbar.LENGTH_LONG).show();
//                    Toast.makeText(context, "Xoá sự kiện yêu thích thành công", Toast.LENGTH_LONG).show();

                } else {
                    FavoriteList favoriteList = new FavoriteList(0, idevents, name, intro, chariman, image);
//                    Log.d(TAG, "onClick1: " + favoriteList.getName());
//                    Log.d(TAG, "onClick1: " + favoriteList.getIdEvent());

                    myDatabaseHelper.insertFavorite(favoriteList);
                    changgColorButon = true;
                    holder.imageButton2.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                    //Log.d(TAG, "Delete Successful: "+v.toString());
                    Snackbar.make(v, "Thêm sự kiện yêu thích thành công", Snackbar.LENGTH_LONG).show();
//                    Toast.makeText(context, "Thêm sự kiện yêu thích thành công", Toast.LENGTH_SHORT).show();


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
        public TextView imageButton2;

        public EventList_holder(View view) {
            super(view);
            name = view.findViewById(R.id.eventnameOnGoingHappen);
            intro = view.findViewById(R.id.eventintroOnGoingHappen);
            day = view.findViewById(R.id.eventdayOnGoingHappen);
            time = view.findViewById(R.id.eventtimeOnGoingHappen);
            place = view.findViewById(R.id.eventplaceOnGoingHappen);
            avatar = view.findViewById(R.id.eventavatarOnGoingHappen);
            cardView = view.findViewById(R.id.linnerOnGoingHappen);
            imageButton2 = view.findViewById(R.id.txtImageFavorite2);
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
            Date datestart = null;
            Date dateend = null;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                datestart = format.parse(ex.getStart_time());
                dateend = format.parse(ex.getEnd_time());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd-MM-yyyy");
//            SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
//            SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
////            String day1 = formatterDate.format(datestart);
//            String day2 = formatterDate.format(dateend);
//            if (day1.equals(day2)){
//                day.setText(formatterTime.format(datestart)+" đến "+formatterTime.format(dateend));
//                time.setText("Ngày: "+day2);
////            holder.check.setText("trùng ngày");
//            } else {
//                day.setText(formatter.format(datestart));
//                time.setText(formatter.format(dateend));
////            holder.check.setText("không trùng ngày");
//            }
            name.setText(ex.getName());
            intro.setText(ex.getIntro());
            day.setText("Bắt đầu: "+formatter.format(datestart));
            time.setText("Kết thúc: "+formatter.format(dateend));
            place.setText(ex.getPlace());
        }
    }
}
