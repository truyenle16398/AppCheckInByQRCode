package com.example.appcheckinbyqrcode.ui.client.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.example.appcheckinbyqrcode.ui.client.model.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    private static final String TAG = "nnn";
    private List<Event> data;
    private Context context;


    public EventAdapter(List<Event> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_client,null,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getEventimage()).into(holder.photo);
        Log.d(TAG, "onBindViewHolder: "+ data.get(position).getEventname());
        holder.name.setText(data.get(position).getEventname());
        holder.day.setText(data.get(position).getEventday());
        holder.address.setText(data.get(position).getEventaddress());
        holder.time.setText(data.get(position).getEventtime());
        holder.note.setText(data.get(position).getEventnote());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{// implements View.OnClickListener

//        @BindView(R.id.eventname)
        public TextView name;
//        @BindView(R.id.eventnote)
        public TextView note;
//        @BindView(R.id.eventday)
        public TextView day;
//        @BindView(R.id.time)
        public TextView time;
//        @BindView(R.id.eventaddress)
        public TextView address;
//        @BindView(R.id.eventimage)
        public ImageView photo;
        public LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.eventname);
            note = view.findViewById(R.id.eventnote);
            day = view.findViewById(R.id.eventday);
            time = view.findViewById(R.id.eventtime);
            address = view.findViewById(R.id.eventaddress);
            photo = view.findViewById(R.id.eventimage);
            linearLayout = view.findViewById(R.id.linner);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: "+ getAdapterPosition());
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}
