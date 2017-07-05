package com.example.android.booklist;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by mr on 16-03-2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, List<Book> book)
    {
        super(context,0,book);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Book currentBook = getItem(position);
        TextView author = (TextView) listItemView.findViewById(R.id.author_id);
        author.setText(currentBook.getAuthor());
        TextView title = (TextView) listItemView.findViewById(R.id.book_title);
        title.setText(currentBook.getTitle());
        TextView publisher = (TextView) listItemView.findViewById(R.id.publisher_id);
        publisher.setText(currentBook.getPublisher());
        return listItemView;
    }
}
