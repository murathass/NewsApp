package com.mynet.interview.mynetapp.Activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mynet.interview.mynetapp.Database.NewsSQL;
import com.mynet.interview.mynetapp.Fragments.NewsListView;
import com.mynet.interview.mynetapp.Models.News;
import com.mynet.interview.mynetapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new NewsListView(),"Haberler");

       /* List<News> newslist = new NewsSQL(getApplicationContext()).getAllData();
        for (News news : newslist){
            Log.e("summary",news.getUuid());
            Log.e("title",news.getTitle());
            Log.e("content",news.getContent());
            Log.e("main_image",news.getMain_image());
        }*/
    }

    public void replaceFragment (Fragment fragment, String title){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;
        Log.d("EKRANLAR",fragmentTag);
        getSupportActionBar().setTitle(title);
        FragmentManager manager = getFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
}
