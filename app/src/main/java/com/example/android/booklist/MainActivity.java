package com.example.android.booklist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{
    public static String api_booklist ="https://www.googleapis.com/books/v1/volumes?q=android&maxResults=30";
    private int load_id=1;
    private TextView mEmptyView;
    private BookAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        ListView listView =(ListView) findViewById(R.id.booklist);
        mEmptyView = (TextView) findViewById(R.id.empty_id);
        mAdapter =new BookAdapter(this,new ArrayList<Book>());
        listView.setAdapter(mAdapter);
        LoaderManager loaderManager =null;
        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected())
        {
            mEmptyView.setVisibility(View.GONE);
            loaderManager = getLoaderManager();
            loaderManager.initLoader(load_id,null,this);
        }
        else
        {
            View loadingIndicator = findViewById(R.id.spinner);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyView.setText(getText(R.string.nic).toString());
        }
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                load_id=load_id+1;
                EditText editText =(EditText) findViewById(R.id.search_badge);
                String query = editText.getText().toString();
                api_booklist= "https://www.googleapis.com/books/v1/volumes?q="+query+"&maxResults=30";
                LoaderManager loaderManager =null;
                ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo!= null && networkInfo.isConnected())
                {
                    mEmptyView.setVisibility(View.GONE);
                    loaderManager = getLoaderManager();
                    loaderManager.initLoader(load_id,null,MainActivity.this);
                }
                else
                {
                    View loadingIndicator =findViewById(R.id.spinner);
                    loadingIndicator.setVisibility(View.GONE);
                    mEmptyView.setText(getText(R.string.nic).toString());
                }
            }
        });
    }
    @Override
    public Loader<List<Book>> onCreateLoader(int i,Bundle bundle)
    {
        return new BookLoader(this,api_booklist);
    }
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> book)
    {
        mEmptyView.setText(getText(R.string.nb).toString());
        mAdapter.clear();
        ProgressBar progressBar =(ProgressBar) findViewById(R.id.spinner);
        progressBar.setVisibility(View.GONE);
        if(book !=null && !book.isEmpty())
        {
            mAdapter.addAll(book);
        }
        else
        {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<Book>> loader)
    {
        mAdapter.clear();
    }
}
