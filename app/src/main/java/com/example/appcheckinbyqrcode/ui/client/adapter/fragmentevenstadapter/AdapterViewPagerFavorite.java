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
import androidx.viewpager.widget.PagerAdapter;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.ui.admin.model.FavoriteList;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdapterViewPagerFavorite extends PagerAdapter {

    private List<FavoriteList> favoriteLists;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterViewPagerFavorite(List<FavoriteList> favoriteLists, Context context) {
        this.favoriteLists = favoriteLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return favoriteLists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_viewpager, container, false);

        ImageView imageView;
        TextView name, intro, chairman;

        imageView = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        intro = view.findViewById(R.id.intro);
        chairman = view.findViewById(R.id.chairman);

        String urls = url.getUrlimgevent() + favoriteLists.get(position).getImage();
////        Glide.with(context).load(urls).into(holder.avatar);
////        Glide.with(context)
////                .load(urls)
////                .diskCacheStrategy(DiskCacheStrategy.NONE)
////                .skipMemoryCache(true)
////                .into(holder.avatar);
        Picasso.get().load(urls).into(imageView);
//
////        holder.getData(items.get(position));
//        imageView.setImageResource();
        name.setText(favoriteLists.get(position).getName());
        intro.setText(favoriteLists.get(position).getIntro());
        chairman.setText(favoriteLists.get(position).getChariman());
        Log.d(TAG, "nnn"+favoriteLists.get(position).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetailActivity.class);
                intent.putExtra("id", favoriteLists.get(position).getIdEvent());
                context.startActivity(intent);
                // finish();
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}