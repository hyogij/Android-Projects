package com.hyogij.jsonclientmasterdetailview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyogij.jsonclientmasterdetailview.Const.Constants;
import com.hyogij.jsonclientmasterdetailview.JsonDatas.Album;
import com.hyogij.jsonclientmasterdetailview.RecyclerViewAdapter.AlbumItemRecycleViewAdapter;
import com.hyogij.jsonclientmasterdetailview.Util.Utils;
import com.hyogij.jsonclientmasterdetailview.Volley.VolleyHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * An activity representing a list of Albums. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the
 * activity presents a list of Albums, which when touched, lead to a {@link
 * PictureListActivity} representing a list of Pictures. On tablets, the
 * activity presents the list of Albums and a list of Pictures side-by-side
 * using two vertical panes.
 */
public class AlbumListActivity extends AppCompatActivity {
    private static final String CLASS_NAME = AlbumListActivity.class
            .getCanonicalName();

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    private ArrayAdapter<Album> arrayAdapter = null;
    private ArrayList<Album> arrayList = null;
    private AlbumItemRecycleViewAdapter adapter = null;

    private View recyclerView = null;
    private EditText editSearch = null;

    private StringBuilder url = null;
    private StringRequest stringRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(Constants.TAG_USERID);
        url = new StringBuilder(Constants.ALBUM_REQUEST_URL);
        url.append(userId);

        // Change an activity name
        setTitle(Utils.getActvityTitle(getString(R.string.albums_activity),
                Constants
                        .TAG_USERID, userId));

        recyclerView = findViewById(R.id.album_list);
        assert recyclerView != null;

        if (findViewById(R.id.album_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true;
        }

        // Search text in the listview
        editSearch = (EditText) findViewById(R.id.search);

        createStringRequest();
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
        Utils.showProgresDialog(this);
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void createStringRequest() {
        // Request a string response from the provided URL
        stringRequest = new StringRequest(Request.Method.GET, url.toString
                (),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgresDialog();

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Album[] array = gson.fromJson(response, Album[].class);
                        arrayList = new ArrayList<Album>(Arrays.asList(array));
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
        adapter = new AlbumItemRecycleViewAdapter(this, arrayList, twoPane);
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
