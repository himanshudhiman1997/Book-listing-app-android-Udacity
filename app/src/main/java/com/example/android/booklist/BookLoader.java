package com.example.android.booklist;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by mr on 16-03-2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String url1;
    public BookLoader(Context context, String url) {
        super(context);
        url1 = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public List<Book> loadInBackground() {
        if (url1 == null) {
            return null;
        }
        List<Book> res =BooksUtils.fetchBooksInfo(url1);
        return res;
    }
}
