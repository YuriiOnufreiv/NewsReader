package com.example.newsreader.newsSources;

import com.example.newsreader.MainNewsItem;
import com.example.newsreader.async.HTMLDocumentGetAsync;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Tsn extends NewsSource {

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
        Elements mainNewsClass = document.select("ul.list_type_1");

        MainNewsItem mainNews = new MainNewsItem();
        Element topNews = mainNewsClass.first();

        mainNews.setTitle(topNews.select("h2.title").text());
        mainNews.setDate(topNews.select("span.date").text());
        mainNews.setTime("");
        mainNews.setLink(link + topNews.select("a[href]").get(0).attr("href"));
        try {
            mainNews.setImage(new HTMLDocumentGetAsync().execute(mainNews.getLink()).get().
                    select("div.picture").get(0).select("a[href]").attr("href"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mainNewsItemArrayList.add(mainNews);
        Element listOfNews = mainNewsClass.get(1);

        for (Element element : listOfNews.children()) {
            mainNews = new MainNewsItem();
            mainNews.setTitle(element.select("h2.title").text());
            mainNews.setDate(element.select("span.date").text());
            mainNews.setTime("");
            mainNews.setLink(link + element.select("a[href]").get(0).attr("href"));
            try {
                mainNews.setImage(new HTMLDocumentGetAsync().execute(mainNews.getLink()).get().
                        select("div.picture").get(0).select("a[href]").attr("href"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            mainNewsItemArrayList.add(mainNews);
        }

        return mainNewsItemArrayList;
    }

    @Override
    protected void initializeInfo() {
        this.name = "ТСН";
        this.link = "http://tsn.ua";
        this.rssFeedLink = "http://tsn.ua/rss";
        this.mainNewsPage = "http://tsn.ua/golovni-novini";
    }
}
