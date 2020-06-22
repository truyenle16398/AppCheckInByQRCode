package com.example.appcheckinbyqrcode.ui.client.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.response.EventFavoriteResponse;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.sqlite.MyDatabaseHelper;
import com.example.appcheckinbyqrcode.ui.client.HistoryDetailActivity;
import com.example.appcheckinbyqrcode.ui.client.fragment.FavoriteEventFragment;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.Favorite_holder> {
    private MyDatabaseHelper myDatabaseHelper;
    private static final String TAG = "nnn";
    private List<EventFavoriteResponse> items;
    private Context context;
    FavoriteEventFragment fm;

    public FavoriteAdapter(List<EventFavoriteResponse> items, Context context) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Favorite_holder holder, int position) {
//        String a = Resources.getSystem().getString(R.string.base_url);
        String urls = url.getUrlimgevent()+ items.get(position).getImage();
        Glide.with(context).load(urls).into(holder.photo);
        holder.name.setText(items.get(position).getName());
        holder.day.setText(items.get(position).getStartTime());
        holder.persion.setText(items.get(position).getEndTime());
        if (items.get(position).getStatus() == 0) {
            holder.check.setText("Đã đăng ký");
        } else {
            holder.check.setText("Đã hủy");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==5555){
            Log.d(TAG, "onActivityResult: okeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        }
    }

    public class Favorite_holder extends RecyclerView.ViewHolder {
        public TextView name, persion, day, check;
        public ImageView photo;
        public CardView cardView;

        public Favorite_holder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.eventname_favorite);
            day = view.findViewById(R.id.eventday_favorite);
            persion = view.findViewById(R.id.eventpersonname_favorite);
            check = view.findViewById(R.id.eventcheck_favorite);
            photo = view.findViewById(R.id.eventimage_favorite);
            cardView = view.findViewById(R.id.carr);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d(TAG, "onClick: "+items.toString());
//                    Toast.makeText(context, "id "+ items.get(getAdapterPosition()).getEventId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, HistoryDetailActivity.class);
                    intent.putExtra("idhistory",items.get(getAdapterPosition()).getEventId());
                    intent.putExtra("code",items.get(getAdapterPosition()).getCode());
                    ((Activity) context).startActivityForResult(intent, FavoriteEventFragment.REQUEST_CODE_CANCEL);
//                    context.startActivityForResult(intent, 5555);
//                    context.startActivity(intent);
                }
            });
        }
    }
}