package com.example.android.booklist;

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

/**
 * Created by mr on 16-03-2017.
 */

public final class BooksUtils {
    private static final String LOG_TAG = BooksUtils.class.getSimpleName();
    private BooksUtils(){
    }
    public static List<Book> fetchBooksInfo(String requestUrl)
    {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try
        {
        jsonResponse = makeHttpRequest(url);
        }
        catch(IOException e)
        {
        }
    List<Book> books =extractFeatureFromJson(jsonResponse);
        return books;
    }
    private static URL createUrl(String stringUrl)
    {
        URL url=null;
        try
        {
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e)
        {}
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code-> " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem in retrieving the earthquake JSON results", e);
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
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }
    private static List<Book> extractFeatureFromJson(String string1) {
        if (TextUtils.isEmpty(string1)) {
            return null;
        }
        List<Book> listBook = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(string1);
            JSONArray array = jsonResponse.getJSONArray("items");
            for(int i=0;i<array.length();i++) {
                JSONObject feature = array.optJSONObject(i);
                JSONObject bkvolumeInfo = feature.optJSONObject("volumeInfo");
                String title = bkvolumeInfo.optString("title");
                String publisher = bkvolumeInfo.optString("publisher");
                String author = bkvolumeInfo.optString("authors");
                listBook.add(new Book(author,title,publisher));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.error), e);
        }
        return listBook;
    }
}
