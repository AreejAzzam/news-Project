package com.example.android.newsprojectstage1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Lenovo on 7/11/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mURL;
    public NewsLoader(Context context, String url)
    {
        super(context);
        mURL=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public List<News> loadInBackground(){
        if(mURL== null)
        {
            return  null;
        }
        List<News>news=Query.feachnewsData(mURL);
        return news;
    }
}

