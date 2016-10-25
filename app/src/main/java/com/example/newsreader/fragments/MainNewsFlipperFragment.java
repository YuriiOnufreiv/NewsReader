package com.example.newsreader.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.myapplication.R;
import com.example.newsreader.MainNewsItem;
import com.example.newsreader.activities.ArticleDetailsActivity;
import com.example.newsreader.async.URLBitmapFactoryAsync;
import com.example.newsreader.newsSources.FootballUa;
import com.example.newsreader.newsSources.NewsSource;
import com.example.newsreader.newsSources.Unian;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainNewsFlipperFragment extends Fragment {

    private NewsSource newsSource;
    private ViewFlipper mainNewsFlipper;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_news_in_flipper_layout, container, false);
        mainNewsFlipper = (ViewFlipper) view.findViewById(R.id.mainNewsFlipper);
        setButtonListeners(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        printMainNews();
    }

    private void setButtonListeners(View view) {
        view.findViewById(R.id.previous_item_button).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainNewsFlipper.setInAnimation(v.getContext(), R.anim.flip_in_previous);
                        mainNewsFlipper.setOutAnimation(v.getContext(), R.anim.flip_out_previous);
                        mainNewsFlipper.showPrevious();
                    }
                });
        view.findViewById(R.id.next_item_button).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainNewsFlipper.setInAnimation(v.getContext(), R.anim.flip_in_next);
                        mainNewsFlipper.setOutAnimation(v.getContext(), R.anim.flip_out_next);
                        mainNewsFlipper.showNext();
                    }
                });
    }

    public void printMainNews() {
        ArrayList<MainNewsItem> mainNewsItemArrayList = getMainNewsArrayList();
        addMainNewsToFlipper(mainNewsItemArrayList);
    }

    private ArrayList<MainNewsItem> getMainNewsArrayList() {
        return newsSource.getMainNews();
    }

    private void addMainNewsToFlipper(ArrayList<MainNewsItem> mainNewsItemArrayList) {
        boolean enough = false;
        for(int i = 0; i < mainNewsItemArrayList.size() && !enough; i++) {
            final MainNewsItem item = mainNewsItemArrayList.get(i);
            View view = View.inflate(getActivity(), R.layout.main_news_item_layout, null);
            ((TextView) view.findViewById(R.id.itemTitle)).setText(item.getTitle());
            ((TextView) view.findViewById(R.id.itemDate)).setText(item.getDate() + ", " + item.getTime());
            try {
                ((ImageView) view.findViewById(R.id.itemImage)).setImageBitmap(new URLBitmapFactoryAsync().
                        execute(item.getImage()).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
                    intent.putExtra("URL", item.getLink());
                    intent.putExtra("SOURCE", item.getTitle());
                    getActivity().startActivity(intent);
                }
            });
            mainNewsFlipper.addView(view);
            if (i == 5) {
                enough = true;
            }
        }
    }
}
