package com.example.newsreader.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

public class NewsFragment extends Fragment {

    private NewsListFragment newsListFragment;
    private MainNewsFlipperFragment mainNewsFlipperFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsListFragment = new NewsListFragment();
        mainNewsFlipperFragment = new MainNewsFlipperFragment();
        Bundle args = getArguments();
        newsListFragment.setArguments(args);
        mainNewsFlipperFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.news_list_fragment, newsListFragment);
        transaction.add(R.id.main_news_flipper, mainNewsFlipperFragment);
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_activity_layout, container, false);
        return view;
    }
}
