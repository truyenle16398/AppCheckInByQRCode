package com.example.appcheckinbyqrcode.ui.client.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import com.example.appcheckinbyqrcode.network.response.EventFavoriteResponse;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.example.appcheckinbyqrcode.ui.client.fragment.FavoriteEventFragment;
import com.example.appcheckinbyqrcode.ui.client.model.Favorite;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.Favorite_holder>  {
    private static final String TAG = "nnn";
    private List<EventFavoriteResponse> items;
    private Context context;


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
        String urls = "http://10.0.2.239:8888/sdc_event/public/"+ items.get(position).getAvatar();
        Glide.with(context).load(urls).into(holder.photo);
        holder.name.setText(items.get(position).getName());
        holder.day.setText(items.get(position).getStartTime());
        holder.persion.setText(items.get(position).getEndTime());
        if (items.get(position).getStatus()==0){
            holder.check.setText("Đã đăng ký");
        } else {
            holder.check.setText("Đã hủy");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class Favorite_holder extends RecyclerView.ViewHolder {
        public TextView name, persion, day, check;
        public ImageView photo;
        public LinearLayout linearLayout;

        public Favorite_holder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.eventname_favorite);
            day = view.findViewById(R.id.eventday_favorite);
            persion = view.findViewById(R.id.eventpersonname_favorite);
            check = view.findViewById(R.id.eventcheck_favorite);
            photo = view.findViewById(R.id.eventimage_favorite);
            linearLayout = view.findViewById(R.id.linner_favorite);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "id "+ items.get(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    intent.putExtra("idhistory",items.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
