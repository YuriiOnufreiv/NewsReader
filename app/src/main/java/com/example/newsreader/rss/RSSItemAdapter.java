package com.example.newsreader.rss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.newsreader.async.URLBitmapFactoryAsync;
import com.example.newsreader.activities.ArticleDetailsActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RSSItemAdapter extends ArrayAdapter<RSSItem> {
    private Context context;
    private ArrayList<RSSItem> feed;

    public RSSItemAdapter(Context context, ArrayList<RSSItem> feed) {
        super(context, R.layout.rss_item_layout, feed);
        this.context = context;
        this.feed = feed;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.rss_item_layout, parent, false);

        TextView textView = (TextView) itemView.findViewById(R.id.itemTitle);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.itemImage);
        final RSSItem currentItem = feed.get(position);

        textView.setText(currentItem.getTitle());
        if(currentItem.getImageLink() != null) {
            Bitmap bmp = currentItem.getImage();
            if(bmp == null) {
                URLBitmapFactoryAsync factory = new URLBitmapFactoryAsync();
                factory.execute(currentItem.getImageLink());
                try {
                    bmp = factory.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                currentItem.setImage(bmp);
            }
            imageView.setImageBitmap(currentItem.getImage());
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ArticleDetailsActivity.class);
                intent.putExtra("URL", currentItem.getLink());
                intent.putExtra("SOURCE", currentItem.getTitle());
                getContext().startActivity(intent);
            }
        });
        return itemView;

        /*
        itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getContext(), ArticleDetailsActivity.class);
                    intent.putExtra("URL", currentItem.getLink());
                    getContext().startActivity(intent);
                }
                return false;
            }
        });
        return itemView;
         */
    }
}
