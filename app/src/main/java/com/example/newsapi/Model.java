package com.example.newsapi;

public class Model {
    String title, date, imageUrl, link, num;

    public Model(String title, String date, String imageUrl, String link, String num) {
        this.title = title;
        this.date = date;
        this.imageUrl = imageUrl;
        this.link = link;
        this.num = num;
    }

    public Model() {
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLink() {
        return link;
    }

    public String getNum() {
        return num;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
