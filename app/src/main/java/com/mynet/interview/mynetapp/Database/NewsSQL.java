package com.mynet.interview.mynetapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mynet.interview.mynetapp.Models.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by murathas on 29.09.2017.
 */

public class NewsSQL extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 4;
    private static final String DATABASE_NAME    = "newssql.db";
    private static final String TABLE_NAME       = "newssql";


    private static String ID         = "id";
    private static String UUID         = "uuid";
    private static String TITLE           = "title";
    private static String SUMMARY          = "summary";
    private static String CONTENT        = "content";
    private static String MAINIMAGE    = "main_image";
    private static String LINK    = "link";


    public NewsSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID      + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UUID      + " TEXT,"
                + TITLE        + " TEXT,"
                + SUMMARY       + " TEXT,"
                + CONTENT     + " TEXT,"
                + MAINIMAGE     + " TEXT,"
                + LINK   + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }


    public boolean insertNews(News news){
        try{

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UUID,   news.getUuid());
            values.put(TITLE, news.getTitle());
            values.put(SUMMARY, news.getSummary());
            values.put(CONTENT, news.getContent());
            values.put(MAINIMAGE, news.getMain_image());
            values.put(LINK, news.getLink());


            db.insert(TABLE_NAME, null, values);
            db.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public List<News> getAllData() {
        List<News> newsses = new ArrayList<News>();

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        try{

            Cursor cursor = db.rawQuery(countQuery, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                News news = cursorToN(cursor);
                newsses.add(news);
                cursor.moveToNext();
            }

            cursor.close();
        }
        catch(Exception hata){}
        finally{db.close();}

        return newsses;
    }

    private News cursorToN(Cursor cursor) {
        News news = new News(
                cursor.getString(cursor.getColumnIndex(UUID)),
                cursor.getString(cursor.getColumnIndex(TITLE)),
                cursor.getString(cursor.getColumnIndex(SUMMARY)),
                cursor.getString(cursor.getColumnIndex(CONTENT)),
                cursor.getString(cursor.getColumnIndex(MAINIMAGE)),
                cursor.getString(cursor.getColumnIndex(LINK))
        );

        return news;
    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public News getNews(String id) {

        News news = new News();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+UUID+"=" + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            news.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            news.setContent(cursor.getString(cursor.getColumnIndex(CONTENT)));
            news.setUuid(cursor.getString(cursor.getColumnIndex(UUID)));
            news.setMain_image(cursor.getString(cursor.getColumnIndex(MAINIMAGE)));
            news.setSummary(cursor.getString(cursor.getColumnIndex(SUMMARY)));
            news.setLink(cursor.getString(cursor.getColumnIndex(LINK)));

        }
        cursor.close();
        db.close();

        return news;
    }
}
