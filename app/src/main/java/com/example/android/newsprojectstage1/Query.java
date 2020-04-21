package com.example.android.newsprojectstage1;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public final class Query {
    private Query() {

    }

    public static final String LOG_TAG = Query.class.getSimpleName();

    private static List<News> extractFeatureFromJson(String newsJson) {
        if (TextUtils.isEmpty(newsJson)) {
            return null;
        }
        List<News> news = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJson);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray NewsArray = response.getJSONArray("results");

            for (int i = 0; i < NewsArray.length(); i++) {
                JSONObject currentNews = NewsArray.getJSONObject(i);
                String Type = currentNews.optString("type");
                String SectionName = currentNews.optString("sectionName");
                String WebtTitle = currentNews.optString("webTitle");
                String Date = currentNews.optString("webPublicationDate");
                String url = currentNews.optString("webUrl");

                JSONArray tags = currentNews.getJSONArray("tags");
                String articleAuthor;
                if (tags.length() != 0) {
                    JSONObject tagsObject = tags.getJSONObject(0);
                    articleAuthor = tagsObject.optString("webTitle");
                } else articleAuthor = "No author, this is just a news";
                News NEWs = new News(SectionName, articleAuthor, WebtTitle, Date, url);
                news.add(NEWs);
            }

        } catch (JSONException e) {
            Log.e("Query", "Problem parsing the earthquake JSON results", e);

        }
        return news;
    }

    public static List<News> feachnewsData(String requestURL) {
        URL url = createUrl(requestURL);
        String jsonRespons = null;
        try {
            jsonRespons = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem while requst the HTTp", e);
        }

        List<News> news = extractFeatureFromJson(jsonRespons);
        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        int readTimeout = 1000, ConnectTimeOut = 15000, respondcode = 200;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.setConnectTimeout(ConnectTimeOut);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == respondcode) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
