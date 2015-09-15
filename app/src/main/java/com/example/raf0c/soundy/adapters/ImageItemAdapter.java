package com.example.raf0c.soundy.adapters;

import android.content.Context;
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
public class ImageItemAdapter extends ArrayAdapter<ImageItem> {

    private ImageLoader mImageLoader;

    public ImageItemAdapter(Context context) {
        super(context, R.layout.list_item);

        mImageLoader = new ImageLoader(ApplicationController.getInstance().getRequestQueue(), new BitmapLruCache());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // NOTE: You would normally use the ViewHolder pattern here
        NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.prof_followers);
        TextView textView = (TextView) convertView.findViewById(R.id.follower_name);

        ImageItem imageItem = getItem(position);

        imageView.setImageUrl(imageItem.getUrl(), mImageLoader);
        textView.setText(imageItem.getTitle());

        return convertView;
    }

    public void swapImageRecords(List<ImageItem> objects) {
        clear();

        for(ImageItem object : objects) {
            add(object);
        }

        notifyDataSetChanged();
    }
}