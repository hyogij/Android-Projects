package com.hyogij.jsonclientmasterdetailview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyogij.jsonclientmasterdetailview.Const.Constants;
import com.hyogij.jsonclientmasterdetailview.JsonDatas.Comment;
import com.hyogij.jsonclientmasterdetailview.JsonRequestUtils.JsonRequestHelper;
import com.hyogij.jsonclientmasterdetailview.RecyclerViewAdapter
        .CommentItemRecycleViewAdapter;
import com.hyogij.jsonclientmasterdetailview.Util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * An activity representing a list of Comments. This activity is only used
 * narrow width devices.
 */
public class CommentListActivity extends AppCompatActivity {
    private static final String CLASS_NAME = CommentListActivity.class
            .getCanonicalName();

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    private JsonRequestHelper jsonRequestHelper = null;

    private ArrayAdapter<Comment> arrayAdapter = null;
    private ArrayList<Comment> arrayList = null;
    private CommentItemRecycleViewAdapter adapter = null;

    private View recyclerView = null;
    private EditText editSearch = null;

    private StringBuilder url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        Intent intent = getIntent();
        String postId = intent.getStringExtra(Constants.TAG_POSTID);
        url = new StringBuilder(Constants.COMMENT_REQUEST_URL);
        url.append(postId);

        // Change an activity name
        setTitle(Utils.getActvityTitle(getString(R.string.comments_activity),
                Constants
                        .TAG_POSTID, postId));

        recyclerView = findViewById(R.id.comment_list);
        assert recyclerView != null;

        if (findViewById(R.id.comment_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true;
        }

        // Search text in the listview
        editSearch = (EditText) findViewById(R.id.search);

        requestJSON();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestJSON() {
        jsonRequestHelper = new JsonRequestHelper(this, handler, url.toString
                ());
    }

    // Handler to wait receiving json data
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Comment[] array = gson.fromJson(jsonRequestHelper
                            .getJsonData(), Comment[].class);
                    arrayList = new ArrayList<Comment>(Arrays.asList(array));
                    onRefreshList();
                    addSearchFilter();
                    break;
                case 1:
                    Utils.showToast(getApplicationContext(), getString(R
                            .string.json_error));
                default:
                    break;
            }
        }
    };

    private void onRefreshList() {
        adapter = new CommentItemRecycleViewAdapter(this, arrayList, twoPane);
        ((RecyclerView) recyclerView).setAdapter(adapter);
    }

    private void addSearchFilter() {
        // Capture Text in EditText
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editSearch.getText().toString().toLowerCase
                        (Locale.getDefault());
                adapter.filter(text);
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
}
