package com.example.newsreader.rss;

import android.os.AsyncTask;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RSSReader {
    private String url;
    private RSSAsyncReader asyncReader;

    public RSSReader(String url) {
        this.url = url;
        asyncReader = new RSSAsyncReader();
    }

    public RSSFeed getFeed() {
        RSSFeed feed = null;
        asyncReader.execute(url);
        try {
            feed = asyncReader.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return feed;
    }

    private static class RSSAsyncReader extends AsyncTask<String, Void, RSSFeed> {

        @Override
        protected RSSFeed doInBackground(String... urls) {

            String url = urls[0];
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = null;
            try {
                saxParser = factory.newSAXParser();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            RSSParserHandler handler = new RSSParserHandler();
            try {
                saxParser.parse(url, handler);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return handler.getFeed();
        }
    }
}
