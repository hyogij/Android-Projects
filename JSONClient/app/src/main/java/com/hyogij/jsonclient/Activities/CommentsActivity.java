package com.hyogij.jsonclient.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyogij.jsonclient.Adapters.CommentAdapter;
import com.hyogij.jsonclient.Const.Constants;
import com.hyogij.jsonclient.JsonDatas.Comment;
import com.hyogij.jsonclient.JsonRequestUtils.JsonRequestHelper;
import com.hyogij.jsonclient.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class CommentsActivity extends Activity {
    private static final String CLASS_NAME = CommentsActivity.class
            .getCanonicalName();

    private JsonRequestHelper jsonRequestHelper = null;

    private ArrayAdapter<Comment> arrayAdapter = null;
    private ArrayList<Comment> commentArrayList = null;
    private CommentAdapter commentAdapter = null;

    private ListView listView = null;
    private EditText editSearch = null;

    private StringBuilder url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_activity);

        Intent intent = getIntent();
        String postId = intent.getStringExtra(Constants.TAG_POSTID);
        url = new StringBuilder(Constants.COMMENT_REQUEST_URL);
        url.append(postId);

        setActvityTitle(postId);

        // Search text in the listview
        editSearch = (EditText) findViewById(R.id.search);
        addSearchFilter();

        listView = (ListView) findViewById(R.id.list);
        requestJSON();
    }

    // Change an activity name
    private void setActvityTitle(String postId) {
        StringBuilder title = new StringBuilder(getString(R.string.comments_activity));
        title.append(" : ");
        title.append(Constants.TAG_POSTID);
        title.append("(");
        title.append(postId);
        title.append(")");
        setTitle(title.toString());
    }

    private void addSearchFilter() {
        // Capture Text in EditText
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                commentAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }
        });
    }

    private void requestJSON() {
        jsonRequestHelper = new JsonRequestHelper(this, handler, url.toString());
    }

    // Handler to wait receiving json data
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Comment[] commentArray = gson.fromJson(jsonRequestHelper.getJsonData(), Comment[]
                            .class);
                    commentArrayList = new ArrayList<Comment>(Arrays.asList(commentArray));
                    onRefreshList();
                    break;
                default:
                    break;
            }
        }
    };

    private void onRefreshList() {
        commentAdapter = new CommentAdapter(this, R.layout.album_item, commentArrayList);
        listView.setAdapter(commentAdapter);
    }
}