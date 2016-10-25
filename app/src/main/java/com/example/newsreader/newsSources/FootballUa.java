package com.example.newsreader.newsSources;

import com.example.newsreader.MainNewsItem;
import com.example.newsreader.async.HTMLDocumentGetAsync;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FootballUa extends NewsSource {
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
        Elements mainNewsClass = document.select("article.main-news");
        Element listOfNews = mainNewsClass.first().select("ul").first();
        for (Element element : listOfNews.children()) {
            if(element.select("isportNews").first() != null)
                break;
            MainNewsItem mainNews = new MainNewsItem();
            String link = element.select("a[href]").get(0).attr("href");
            mainNews.setLink(link);
            Document doc = null;
            try {
                doc = new HTMLDocumentGetAsync().execute(link).get();
                mainNews.setImage(doc.select("div.article-photo").get(0).select("img").first().attr("src"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
            mainNews.setTitle(element.select("a[href]").get(0).text());
            mainNews.setDate(doc.select("span.photo-date").text());
            mainNews.setTime("");

            mainNewsItemArrayList.add(mainNews);
        }

        return mainNewsItemArrayList;
    }

    @Override
    protected void initializeInfo() {
        this.name = "Football.ua — Новости футбола";
        this.link = "http://football.ua/";
        this.rssFeedLink = "http://football.ua/rss2.ashx";
        this.mainNewsPage = "http://football.ua/";
    }
}
