package com.example.newsreader.newsSources;

import com.example.newsreader.MainNewsItem;
import com.example.newsreader.rss.RSSFeed;
import com.example.newsreader.rss.RSSReader;

import java.util.ArrayList;

public abstract class NewsSource {
    protected String name;
    protected String link;
    protected String rssFeedLink;
    protected String mainNewsPage;

    public NewsSource() {
        this.initializeInfo();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRssFeedLink() {
        return rssFeedLink;
    }

    public RSSFeed getRSSFeed() {
        RSSReader rssReader = new RSSReader(rssFeedLink);
        RSSFeed feed = null;
        try {
            feed = rssReader.getFeed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feed;
    }

    abstract public ArrayList<MainNewsItem> getMainNews();

    abstract protected void initializeInfo();
}
