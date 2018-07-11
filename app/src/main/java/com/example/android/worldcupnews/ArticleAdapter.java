package com.example.android.worldcupnews;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Activity context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the article object located at this position in the list
        Article currentArticle = getItem(position);

        // Find the TextView with id 'Title' and set the relevant title to this view
        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(currentArticle.getTitle());

        // Find the TextView with id 'Author' and set the relevant section to this view
        TextView authorView = listItemView.findViewById(R.id.author);
        authorView.setText("by " + currentArticle.getAuthor());

        // Find the TextView with id 'Section' and set the relevant section to this view
        TextView sectionView = listItemView.findViewById(R.id.section);
        sectionView.setText(currentArticle.getSection());

        // Get the article time in raw format
        String timeString = currentArticle.getTime();

        // Find the TextView with id 'Date' and set the relevant date to this view
        TextView dateView = listItemView.findViewById(R.id.date);
        String month = timeString.substring(5, 7);
        String day = timeString.substring(8,10);
        dateView.setText(day + "/" + month);

        // Find the TextView with id 'Time' and set the relevant time to this view
        TextView timeView = listItemView.findViewById(R.id.time);
        String time = timeString.substring(11,16);
        timeView.setText(time);

        return listItemView;
    }

}
