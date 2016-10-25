package com.example.newsreader.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.newsreader.fragments.MainNewsFlipperFragment;
import com.example.newsreader.fragments.NewsFragment;
import com.example.newsreader.fragments.NewsListFragment;

public class MainActivityWithDrawer extends FragmentActivity {

    private String [] sourceTitles;

    private DrawerLayout drawerLayout;
    private ListView leftDrawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private MainNewsFlipperFragment mainNewsFlipperFragment;
    private NewsListFragment newsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_with_drawer_layout);

        sourceTitles = new String[]{"Unian", "Football.ua"};
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.entire_drawer_layout);

        leftDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sourceTitles));
        leftDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                showSelectedSource(position);
            }

            private void showSelectedSource(int position) {
                Fragment fragment = new NewsFragment();
                Bundle args = new Bundle();
                switch(position) {
                    case 0:
                        args.putString("Source", "Unian");
                        break;
                    case 1:
                        args.putString("Source", "Football.ua");
                        break;
                }
                fragment.setArguments(args);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_frame, fragment).commit();

                leftDrawerList.setItemChecked(position, true);
                setTitle(sourceTitles[position]);
                drawerLayout.closeDrawer(leftDrawerList);

            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,  R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Головне меню");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle("Новини");
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        if(savedInstanceState == null) {
            drawerLayout.openDrawer(leftDrawerList);
        }

        mainNewsFlipperFragment = (MainNewsFlipperFragment)getSupportFragmentManager().findFragmentById(R.id.main_news_flipper);
        newsListFragment = (NewsListFragment)getSupportFragmentManager().findFragmentById(R.id.news_list_fragment);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_list_activity_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            /*case R.id.action_refresh:
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
                return true;*/
            case R.id.action_close:
                finish();
                return true;
            /*case R.id.action_refresh:
                newsListFragment.printFeed();
                mainNewsFlipperFragment.printMainNews();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
        //}
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
