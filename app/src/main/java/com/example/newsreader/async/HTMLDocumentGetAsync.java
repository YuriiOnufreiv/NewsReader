package com.example.newsreader.async;

import android.os.AsyncTask;

import com.example.newsreader.activities.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HTMLDocumentGetAsync extends AsyncTask<String, Void, Document> {

    @Override
    protected Document doInBackground(String... params) {
        Document document = null;
        try {
            document = Jsoup.connect(params[0]).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
