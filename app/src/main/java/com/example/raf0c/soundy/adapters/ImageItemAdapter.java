package com.example.raf0c.soundy.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.raf0c.soundy.R;
import com.example.raf0c.soundy.controller.ApplicationController;
import com.example.raf0c.soundy.model.ImageItem;
import com.example.raf0c.soundy.utils.BitmapLruCache;

import java.util.List;

/**
 * Created by raf0c on 15/09/15.
 */
public class ImageItemAdapter extends RecyclerView.Adapter<ImageItemAdapter.SoundyViewHolder> {

    private ImageLoader mImageLoader;
    private  List<ImageItem> rowItem;
    private Context mContext;

    public ImageItemAdapter(Context context, List<ImageItem> rowItem) {
        this.rowItem = rowItem;
        this.mContext = context;
        mImageLoader = new ImageLoader(ApplicationController.getInstance().getRequestQueue(), new BitmapLruCache());
    }

    public static class SoundyViewHolder extends RecyclerView.ViewHolder{

        TextView follower_name;
        NetworkImageView follower_pic;
        CardView cardview;

        public SoundyViewHolder(View itemView) {
            super(itemView);
            follower_name = (TextView) itemView.findViewById(R.id.follower_name);
            follower_pic = (NetworkImageView) itemView.findViewById(R.id.prof_followers);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public SoundyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        SoundyViewHolder pvh = new SoundyViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(SoundyViewHolder soundyViewHolder, int i) {
        soundyViewHolder.follower_name.setText(rowItem.get(i).getTitle());
        soundyViewHolder.follower_pic.setImageUrl(rowItem.get(i).getUrl(), mImageLoader);
    }

    @Override
    public int getItemCount() {
        return rowItem.size();
    }


}