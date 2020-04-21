package com.example.android.newsprojectstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
        }
        final News currentNews = getItem(position);
        TextView SectionTExtView = (TextView) listItemView.findViewById(R.id.sectionNameId);


        SectionTExtView.setText(currentNews.getSectionName());

        TextView TypeTextView = (TextView) listItemView.findViewById(R.id.TypeId);
        TypeTextView.setText(currentNews.getType());

        TextView webTitleTextView = (TextView) listItemView.findViewById(R.id.descriptionId);
        webTitleTextView.setText(currentNews.getWebtTitle());
        String Date=currentNews.getDateUpdate();
        String date,time;
        date=Date.substring(0,9);
        time=Date.substring(11,Date.length()-4);

        TextView DateTextView = (TextView) listItemView.findViewById(R.id.UpdateDate);
        DateTextView.setText(date);
        TextView TimeTextView = (TextView) listItemView.findViewById(R.id.time);
        TimeTextView.setText(time);
        return listItemView;
    }


}
