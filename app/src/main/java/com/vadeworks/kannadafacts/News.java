package com.vadeworks.kannadafacts;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by ashwinchandlapur on 10/02/18.
 */

public class News implements Serializable {
    public String head;
    public String link;
    public String imgurl;
    public String content;
    public String tag;
    public String thumburl;
    public String subtag;

    public News() {
        this.head = "";
        this.link = "";
        this.imgurl = "";
        this.content = "";
        this.thumburl = "";
        this.tag = "";
        this.subtag = "";
    }


    public News(String head, String link) {
        this.head = head;
        this.link = link;
        this.imgurl = "";
        this.content = "";
        this.thumburl = "";
        this.tag = "";
        this.subtag = "";
    }

    public News(String head, String link, String imgurl) {
        this.head = head;
        this.link = link;
        this.imgurl = imgurl;
        this.content = "";
        this.thumburl = "";
        this.tag = "";
        this.subtag = "";
    }


    public News(String head, String link, String imgurl, String content) {
        this.head = head;
        this.link = link;
        this.imgurl = imgurl;
        this.content = content;
        this.thumburl = "";
        this.tag = "";
        this.subtag = "";
    }


    public News(String head, String link, String imgurl, String content, String thumburl) {
        this.head = head;
        this.link = link;
        this.imgurl = imgurl;
        this.content = content;
        this.thumburl = thumburl;
        this.tag = "";
        this.subtag = "";
    }

    public News(String thumburl, String imgurl, String tag, String content, String link, String head, String subtag) {
        this.head = head;
        this.link = link;
        this.imgurl = imgurl;
        this.content = content;
        this.thumburl = thumburl;
        this.tag = tag;
        this.subtag = subtag;
    }

    public boolean isEmpty() {
        return ((this.head.isEmpty()) || (this.imgurl.isEmpty()) || (this.content.isEmpty() || (this.link.isEmpty()) || (this.thumburl.isEmpty())));
    }

    public void showNews() {
        Log.d("news-info", "Headline is    " + this.head);
        Log.d("news-info", "Link is   " + this.link);
        Log.d("news-info", "imageUrl is   " + this.imgurl);
        Log.d("news-info", "Thumb is " + this.thumburl);
        Log.d("news-info", "Content is " + this.content);
    }


}
