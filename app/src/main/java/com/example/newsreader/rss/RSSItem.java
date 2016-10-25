package com.example.newsreader.rss;

import android.graphics.Bitmap;

public class RSSItem {
    private String title;
    private String link;
    private String imageLink;
    private Bitmap image;
    private String pubDate;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        StringBuilder builder;
        boolean emptyTitle = (this.title == null);
        if(emptyTitle)
            builder = new StringBuilder();
        else
            builder = new StringBuilder(this.title);
        builder.append(title);
        this.title = builder.toString();
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return title + "\n    " +
                "Date - " + pubDate + "\n    " +
                "Link - " + link + "\n    " +
                "Image - " + imageLink + "\n    ";
    }
}
