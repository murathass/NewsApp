package com.mynet.interview.mynetapp.Models;

/**
 * Created by murathas on 28.09.2017.
 */

public class News {


    private String  uuid;
    private String title;
    private String summary;
    private String content;
    private String main_image;
    private String link;

    public News(){

    }

    public News(String uuid, String title, String summary, String content, String main_image, String link) {

        this.uuid = uuid;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.main_image = main_image;
        this.link = link;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
