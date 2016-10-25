package com.example.newsreader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import com.example.myapplication.R;
import com.example.newsreader.fragments.MainNewsFlipperFragment;
import com.example.newsreader.fragments.NewsListFragment;
import com.example.newsreader.newsSources.*;

public class MainActivity extends FragmentActivity {

    private NewsListFragment newsListFragment;
    private MainNewsFlipperFragment mainNewsFlipperFragment;

    private ViewFlipper mainNewsFlipper;

    private NewsSource newsSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);


        newsSource = new Unian();

        newsListFragment = new NewsListFragment();
        mainNewsFlipperFragment = new MainNewsFlipperFragment();

        Intent intent = new Intent();
        intent.putExtra("Source", "Unian");
        Bundle args = intent.getExtras();
        newsListFragment.setArguments(args);
        mainNewsFlipperFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.news_list_fragment, newsListFragment);
        transaction.add(R.id.main_news_flipper, mainNewsFlipperFragment);
        transaction.commit();

        setTitle(newsSource.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_list_activity_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainNewsFlipper = (ViewFlipper) findViewById(R.id.mainNewsFlipper);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                newsListFragment.printFeed();
                mainNewsFlipperFragment.printMainNews();
                return true;
            case R.id.action_startOrStop:
                if(mainNewsFlipper.isFlipping()) {
                    mainNewsFlipper.stopFlipping();
                    item.setIcon(R.drawable.ic_action_play);
                }
                else {
                    mainNewsFlipper.setInAnimation(this, R.anim.flip_in_next);
                    mainNewsFlipper.setOutAnimation(this, R.anim.flip_out_next);
                    mainNewsFlipper.setFlipInterval(5000);
                    mainNewsFlipper.startFlipping();
                    item.setIcon(R.drawable.ic_action_pause);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
