package com.mynet.interview.mynetapp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mynet.interview.mynetapp.Activities.MainActivity;
import com.mynet.interview.mynetapp.Adapters.NewsListAdapter;
import com.mynet.interview.mynetapp.Database.NewsSQL;
import com.mynet.interview.mynetapp.Models.News;
import com.mynet.interview.mynetapp.R;

import java.util.List;

/**
 * Created by murathas on 28.09.2017.
 */

public class NewsListView extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private List<News> newsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view ;

        view = inflater.inflate(R.layout.news_list_fragment,container,false);

        listView = (ListView)view.findViewById(R.id.list);
        newsList = new NewsSQL(getActivity()).getAllData();
        listView.setAdapter(new NewsListAdapter(getActivity(),newsList));
        listView.setOnItemClickListener(this);

        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        News news = newsList.get(i);
        Log.e("title",news.getTitle());

        NewsContent newsContent = new NewsContent();
        Bundle bundle = new Bundle();
        bundle.putString("uuid", news.getUuid());
        newsContent.setArguments(bundle);

        ((MainActivity)getActivity()).replaceFragment(newsContent,"Haber Detayı");

    }
}
