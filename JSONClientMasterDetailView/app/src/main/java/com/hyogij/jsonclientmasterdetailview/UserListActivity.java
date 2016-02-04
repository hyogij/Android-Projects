package com.hyogij.jsonclientmasterdetailview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyogij.jsonclientmasterdetailview.json.User;
import com.hyogij.jsonclientmasterdetailview.adapter.UserItemRecycleViewAdapter;
import com.hyogij.jsonclientmasterdetailview.util.Utils;
import com.hyogij.jsonclientmasterdetailview.volley.VolleyHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * An activity representing a list of Users. This activity is only used
 * narrow width devices. The activity presents a list of Users, which when
 * album button is clicked, lead to a {@link AlbumListActivity} representing
 * a list of Albums. And when post button is clicked, lead to a {@link
 * PostListActivity} representing a list of Posts.
 */
public class UserListActivity extends AppCompatActivity {
    private static final String CLASS_NAME = UserListActivity.class
            .getCanonicalName();

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    private ArrayAdapter<User> arrayAdapter = null;
    private ArrayList<User> arrayList = null;
    private UserItemRecycleViewAdapter adapter = null;

    private View recyclerView = null;
    private EditText editSearch = null;

    private StringRequest stringRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.user_list);
        assert recyclerView != null;

        if (findViewById(R.id.user_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            twoPane = true;
        }

        // Search text in the listview
        editSearch = (EditText) findViewById(R.id.search);

        createStringRequest();
        requestJSON();
    }

    private void requestJSON() {
        Utils.showProgresDialog(this);
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void createStringRequest() {
        // Request a string response from the provided URL
        stringRequest = new StringRequest(Request.Method.GET, Constants
                .USER_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgresDialog();

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        User[] array = gson.fromJson(response, User[].class);
                        arrayList = new ArrayList<User>(Arrays.asList(array));
                        onRefreshList();
                        addSearchFilter();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.hideProgresDialog();

                Utils.showToast(getApplicationContext(), getString(R
                        .string.json_error));
            }
        });
    }

    private void onRefreshList() {
        adapter = new UserItemRecycleViewAdapter(this, arrayList, twoPane);
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
