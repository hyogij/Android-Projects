package com.hyogij.androidassignmentnew.database;

/*
 * Class for Book
 * Date : 2015.11.10
 * Author : hyogij@gmail.com
 * Reference : http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */
public class Book {
    private int id = 0;
    private String title = null;
    private String author = null;

    public Book() {
        super();
    }

    public Book(String title, String author) {
        super();
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author
                + "]\n";
    }
}
