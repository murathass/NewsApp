package com.mynet.interview.mynetapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mynet.interview.mynetapp.Models.News;
import com.mynet.interview.mynetapp.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by murathas on 29.09.2017.
 */

public class NewsListAdapter extends BaseAdapter {


    private List<News> mlist;
    private Context context;
    private LayoutInflater mInflater;

    public NewsListAdapter(Context context, List<News> list){
        this.context=context;
        this.mlist=list;
        mInflater = (LayoutInflater) this.context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.news_list_item,null);
        News news = mlist.get(i);
        ImageView iv = (ImageView)v.findViewById(R.id.imageView);
        TextView tv = (TextView) v.findViewById(R.id.textView);

        Picasso.with(context).load(news.getMain_image()).into(iv);
        tv.setText(news.getTitle());
        return v;
    }
}
