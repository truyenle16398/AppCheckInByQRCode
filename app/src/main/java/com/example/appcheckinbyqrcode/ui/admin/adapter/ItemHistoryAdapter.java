package com.example.appcheckinbyqrcode.ui.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.ui.admin.model.HistoryCheckIn;

import java.util.ArrayList;


public class ItemHistoryAdapter extends RecyclerView.Adapter<ItemHistoryAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<HistoryCheckIn> listHistory;

    public ItemHistoryAdapter(Context context, ArrayList<HistoryCheckIn> listHistory) {
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
        Glide.with(context).load(listHistory.get(position).getmImage()).into(holder.imageView);
        holder.txtName.setText(listHistory.get(position).getmName());
        holder.txtTimeCheckInOut.setText(listHistory.get(position).getmTimeCheck());
        holder.txtEventName.setText(listHistory.get(position).getmEventName());


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
            imageView = itemView.findViewById(R.id.imageView);
            txtName = itemView.findViewById(R.id.txtName);
            txtTimeCheckInOut = itemView.findViewById(R.id.txtTimeCheckInOut);
            txtEventName = itemView.findViewById(R.id.txtEventName);

        }
    }
}

