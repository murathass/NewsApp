package com.mynet.interview.mynetapp.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mynet.interview.mynetapp.Adapters.NewsListAdapter;
import com.mynet.interview.mynetapp.Database.NewsSQL;
import com.mynet.interview.mynetapp.Models.News;
import com.mynet.interview.mynetapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by murathas on 28.09.2017.
 */

public class NewsContent extends Fragment {


    private News selectedNews;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view ;
        view = inflater.inflate(R.layout.news_list_content,container,false);

        ImageView image = view.findViewById(R.id.content_image);
        WebView webView = view.findViewById(R.id.content_web);
        (view.findViewById(R.id.content_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = selectedNews.getLink();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
            }
        });


        Bundle bundle = this.getArguments();
        String uuid = bundle.getString("uuid", "");


        if (!uuid.equals("")){
            selectedNews = new NewsSQL(getActivity()).getNews(uuid);
            Picasso.with(getActivity()).load(selectedNews.getMain_image()).into(image);
            webView.loadData(selectedNews.getContent(), "text/html; charset=utf-8", "UTF-8");
            Log.e("CONTENT",selectedNews.getSummary());
            Log.e("CONTENT",selectedNews.getTitle());
            Log.e("CONTENT",selectedNews.getMain_image());
            Log.e("CONTENT",selectedNews.getUuid());
            Log.e("CONTENT",selectedNews.getContent());
            Log.e("CONTENT",selectedNews.getLink());
        }

        return view;

    }

}
