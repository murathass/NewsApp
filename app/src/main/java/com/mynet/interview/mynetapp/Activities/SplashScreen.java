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
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.mynet.interview.mynetapp.Database.NewsSQL;
import com.mynet.interview.mynetapp.Models.News;
import com.mynet.interview.mynetapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


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
            Log.e("network","true");
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!isFinishing()){
                       final AlertDialog.Builder alert =  new AlertDialog.Builder(SplashScreen.this)
                                .setTitle("Uyarı!")
                                .setMessage("İnternet Bağlantınızı Kontrol Ediniz!")
                                .setCancelable(false)
                                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SplashScreen.this.finishAffinity();
                                        System.exit(0);
                                    }
                                });
                        alert.show();
                    }
                }
            });

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