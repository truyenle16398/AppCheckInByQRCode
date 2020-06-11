package com.example.appcheckinbyqrcode.ui.admin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.ui.admin.model.HistoryCheckIn;
import com.example.appcheckinbyqrcode.ui.admin.model.InfoQR;

import java.util.ArrayList;


public class ItemHistoryAdapter extends RecyclerView.Adapter<ItemHistoryAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<InfoQR> listHistory;

    public ItemHistoryAdapter(Context context, ArrayList<InfoQR> listHistory) {
        this.context = context;
        this.listHistory = listHistory;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_adapter_history, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, final int position) {
//        holder.imageView.setImageDrawable(getListMountain().get(position).getmImage());
        InfoQR infoQR = listHistory.get(position);
        String urls = url.getUrlimg()+ infoQR.avatar;
        Glide.with(context).load(urls).into(holder.imageView);
        holder.txtEventName.setText(infoQR.name);
        holder.txtName.setText(infoQR.email);
        holder.txtTimeCheckInOut.setText(infoQR.timecheckin);
        Log.d("nnn", "onBindViewHolder: "+infoQR.timecheckin);
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtName, txtTimeCheckInOut, txtEventName;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.eventimage_favorite);
            txtName = itemView.findViewById(R.id.eventname_favorite);
            txtTimeCheckInOut = itemView.findViewById(R.id.txtTimeCheckInOut);
            txtEventName = itemView.findViewById(R.id.txtEventName);

        }
    }
}

