package com.example.newsreader.newsSources;

import com.example.newsreader.async.HTMLDocumentGetAsync;
import com.example.newsreader.MainNewsItem;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Unian extends NewsSource {

    @Override
    public ArrayList<MainNewsItem> getMainNews() {
        ArrayList<MainNewsItem> mainNewsItemArrayList = new ArrayList<MainNewsItem>();
        Document document = null;
        try {
            document = new HTMLDocumentGetAsync().execute(mainNewsPage).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Elements mainNewsClass = document.select("div.other_news");
        Element listOfNews = mainNewsClass.first().select("ul").first();
        for (Element element : listOfNews.children()) {
            MainNewsItem mainNews = new MainNewsItem();
            String link = element.select("a[href]").get(0).attr("href");
            mainNews.setLink(link);
            try {
                mainNews.setImage(new HTMLDocumentGetAsync().execute(link).get().
                        select("div.photo_block").get(0).select("img").first().attr("src"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            Element classHolder = element.select("span.holder").first();
            mainNews.setTitle(classHolder.select("span.title").text());
            mainNews.setDate(classHolder.select("span.date").text());
            mainNews.setTime(element.select("span.time").text());

            mainNewsItemArrayList.add(mainNews);
        }

        return mainNewsItemArrayList;
    }

    @Override
    protected void initializeInfo() {
        this.name = "Інформаційне агенство УНІАН";
        this.link = "http://www.unian.ua";
        this.rssFeedLink = "http://rss.unian.net/site/news_ukr.rss";
        this.mainNewsPage = "http://www.unian.ua/detail/main_news";
    }
}
