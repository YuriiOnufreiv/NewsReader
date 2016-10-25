package com.example.newsreader.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.example.newsreader.newsSources.FootballUa;
import com.example.newsreader.newsSources.NewsSource;
import com.example.newsreader.newsSources.Unian;
import com.example.newsreader.rss.RSSFeed;
import com.example.newsreader.rss.RSSItemAdapter;

public class NewsListFragment extends ListFragment {

    private NewsSource newsSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String sourceID = getArguments().getString("Source");
        if(sourceID.equals("Unian"))
            newsSource = new Unian();
        else if(sourceID.equals("Football.ua"))
            newsSource = new FootballUa();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        printFeed();
    }

    public void printFeed() {
        RSSFeed feed = newsSource.getRSSFeed();
        RSSItemAdapter adapter = new RSSItemAdapter(getActivity(), feed.getFeed());
        setListAdapter(adapter);
    }
}
