package com.hyogij.androidassignmentnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hyogij.androidassignmentnew.database.Book;
import com.hyogij.androidassignmentnew.database.MySQLiteHelper;

import java.util.List;

/*
 * Add and read books from database which uses sqlite
 * Date : 2015.11.10
 * Author : hyogij@gmail.com
 * Reference : http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */
public class DatabaseResultActivity extends AppCompatActivity {
    private static final String CLASS_NAME = JsonResultActivity.class.getCanonicalName();

    private TextView txtResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_result);

        // get reference to the views
        txtResult = (TextView) findViewById(R.id.txtResult);

        readDatabase();
    }

    public void onClose(View view) {
        finish();
    }

    private void readDatabase() {
        MySQLiteHelper db = new MySQLiteHelper(this);

        // Add Books
        db.addBook(new Book("Android Application Development Cookbook", "Wei Meng Lee"));
        db.addBook(new Book("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));
        db.addBook(new Book("Learn Android App Development", "Wallace Jackson"));

        // Get all books
        List<Book> list = db.getAllBooks();

        // Delete one book
        // db.deleteBook(list.get(0));

        // Print all books' informations
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Book book = list.get(i);
            sb.append(book.toString());
        }
        txtResult.setText(sb.toString());
    }
}
