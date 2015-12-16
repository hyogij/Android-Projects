package com.hyogij.jsonclient.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyogij.jsonclient.Adapters.PostAdapter;
import com.hyogij.jsonclient.Const.Constants;
import com.hyogij.jsonclient.JsonDatas.Post;
import com.hyogij.jsonclient.JsonRequestUtils.JsonRequestHelper;
import com.hyogij.jsonclient.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class PostsActivity extends Activity {
    private static final String CLASS_NAME = PostsActivity.class
            .getCanonicalName();

    private JsonRequestHelper jsonRequestHelper = null;

    private ArrayAdapter<Post> arrayAdapter = null;
    private ArrayList<Post> postArrayList = null;
    private PostAdapter postAdapter = null;

    private ListView listView = null;
    private EditText editSearch = null;

    private StringBuilder url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_activity);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(Constants.TAG_USERID);
        url = new StringBuilder(Constants.POST_REQUEST_URL);
        url.append(userId);

        setActvityTitle(userId);

        // Search text in the listview
        editSearch = (EditText) findViewById(R.id.search);
        addSearchFilter();

        listView = (ListView) findViewById(R.id.list);
        requestJSON();
    }

    // Change an activity name
    private void setActvityTitle(String userId) {
        StringBuilder title = new StringBuilder(getString(R.string.posts_activity));
        title.append(" : ");
        title.append(Constants.TAG_USERID);
        title.append("(");
        title.append(userId);
        title.append(")");
        setTitle(title.toString());
    }

    private void addSearchFilter() {
        // Capture Text in EditText
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                postAdapter.filter(text);
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
                    Post[] postArray = gson.fromJson(jsonRequestHelper.getJsonData(), Post[]
                            .class);
                    postArrayList = new ArrayList<Post>(Arrays.asList(postArray));
                    onRefreshList();
                    break;
                default:
                    break;
            }
        }
    };

    private void onRefreshList() {
        postAdapter = new PostAdapter(this, R.layout.album_item, postArrayList);
        listView.setAdapter(postAdapter);
        listView.setOnItemClickListener(onClickListItem);
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String postId = postArrayList.get(arg2).getId();
            Intent intent = new Intent(PostsActivity
                    .this, CommentsActivity.class);
            intent.putExtra(Constants.TAG_POSTID, postId);
            startActivity(intent);
        }
    };
}