package com.example.newsreader.newsSources;

import com.example.newsreader.MainNewsItem;
import com.example.newsreader.rss.RSSItem;

import java.util.ArrayList;

public class UkrPravda extends NewsSource {
    @Override
    public ArrayList<MainNewsItem> getMainNews() {
        String rssFeedLinkRemember = this.rssFeedLink;
        this.rssFeedLink = mainNewsPage;
        ArrayList<MainNewsItem> mainNews = new ArrayList<MainNewsItem>();
        ArrayList<RSSItem> mainNewsFeed = this.getRSSFeed().getFeed();
        for(RSSItem item : mainNewsFeed) {
            MainNewsItem mainNewsItem = new MainNewsItem();
            mainNewsItem.setLink(item.getLink());
            mainNewsItem.setImage(item.getImageLink());
            mainNewsItem.setTitle(item.getTitle());
            mainNewsItem.setDate(item.getPubDate());
            mainNewsItem.setTime("");
            mainNews.add(mainNewsItem);
        }
        this.rssFeedLink = rssFeedLinkRemember;
        return mainNews;
    }

    @Override
    protected void initializeInfo() {
        this.name = "Українська правда";
        this.link = "http://www.pravda.com.ua";
        this.rssFeedLink = "http://www.pravda.com.ua/rss/";
        this.mainNewsPage = "http://www.pravda.com.ua/rss/view_mainnews/";
    }
}
