package com.mynet.interview.mynetapp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mynet.interview.mynetapp.Database.NewsSQL;
import com.mynet.interview.mynetapp.Helper.MyApplication;
import com.mynet.interview.mynetapp.Models.News;
import com.mynet.interview.mynetapp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by murathas on 28.09.2017.
 */

public class SplashScreen extends Activity {

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        if (isConnected()){
            new PrefetchData().execute();
        }

    }

    private class PrefetchData extends AsyncTask<Void, Void, String> {

        InputStream inputStream = null;
        String result = "";

        @Override
        protected String doInBackground(Void... params) {
            try {

                String url = "http://www.mocky.io/v2/59cc13f726000062106b773d";

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null){
                Toast.makeText(getApplicationContext(),"Internet Bağlantınızı Kontrol Ediniz!",Toast.LENGTH_SHORT).show();

                finishAffinity();
                System.exit(0);
                return;
            }
            try {
                JSONArray arr = new JSONArray(result);
                NewsSQL sql = new NewsSQL(getApplicationContext());
                sql.deleteTable();
                for (int i = 0; i < arr.length(); i++) {
                    News news = new News();
                    JSONObject obj = arr.getJSONObject(i);
                    news.setUuid(obj.getString("uuid"));
                    news.setTitle(obj.getString("title"));
                    news.setContent(obj.getString("content"));
                    news.setSummary(obj.getString("summary"));
                    if (obj.has("share_url")){
                        news.setLink(obj.getString("share_url"));
                    }else if(obj.has("json_url")){
                        news.setLink(obj.getString("json_url"));
                    }else{
                        news.setLink("link");
                    }

                    Log.e("linkik",news.getLink());
                    String image_url = obj.getJSONObject("main_image").getString("url");
                    news.setMain_image(image_url);
                    sql.insertNews(news);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);

            finish();
        }

    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


}