package com.example.android.booklist;

/**
 * Created by mr on 16-03-2017.
 */

public class Book {
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    public Book(String author,String title,String publisher)
    {
        bookAuthor=author;
        bookPublisher=publisher;
        bookTitle=title;
    }
    public String getTitle()
    {
        return bookTitle;
    }
    public String getAuthor()
    {
        return bookAuthor;
    }
    public String getPublisher()
    {
        return bookPublisher;
    }

}
