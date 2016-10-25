package com.example.newsreader.rss;

import java.util.ArrayList;

public class RSSFeed {
    public ArrayList<RSSItem> feed;

    public RSSFeed() {
        feed = new ArrayList<RSSItem>();
    }

    public void addItem(RSSItem item) {
        feed.add(item);
    }

    public ArrayList<RSSItem> getFeed() {
        return feed;
    }
}
