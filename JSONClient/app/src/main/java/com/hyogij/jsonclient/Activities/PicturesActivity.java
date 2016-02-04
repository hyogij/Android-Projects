package com.hyogij.jsonclient.activities;

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
import com.hyogij.jsonclient.adapters.PictureAdapter;
import com.hyogij.jsonclient.Constants;
import com.hyogij.jsonclient.data.Picture;
import com.hyogij.jsonclient.helper.JsonRequestHelper;
import com.hyogij.jsonclient.R;
import com.hyogij.jsonclient.helper.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by hyogij on 2015. 12. 12..
 */
public class PicturesActivity extends Activity {
    private static final String CLASS_NAME = PicturesActivity.class
            .getCanonicalName();

    private JsonRequestHelper jsonRequestHelper = null;

    private ArrayAdapter<Picture> arrayAdapter = null;
    private ArrayList<Picture> pictureArrayList = null;
    private PictureAdapter lazyAdapter = null;

    private ListView listView = null;
    private EditText editSearch = null;

    private StringBuilder url = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pictures_activity);

        Intent intent = getIntent();
        String albumId = intent.getStringExtra(Constants.TAG_ALBUMID);
        url = new StringBuilder(Constants.PICTURE_REQUEST_URL);
        url.append(albumId);

        // Change an activity name
        setTitle(StringUtils.getActvityTitle(getString(R.string.pictures_activity), Constants
                .TAG_ALBUMID, albumId));

        // Search text in the listview
        editSearch = (EditText) findViewById(R.id.search);
        addSearchFilter();

        listView = (ListView) findViewById(R.id.list);
        requestJSON();
    }

    private void addSearchFilter() {
        // Capture Text in EditText
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                lazyAdapter.filter(text);
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
                    Picture[] pictureArray = gson.fromJson(jsonRequestHelper.getJsonData(),
                            Picture[].class);
                    pictureArrayList = new ArrayList<Picture>(Arrays.asList(pictureArray));
                    onRefreshList();
                    break;
                default:
                    break;
            }
        }
    };

    private void onRefreshList() {
        // Updating parsed JSON data into ListView
        lazyAdapter = new PictureAdapter(this, R.layout.picture_item, pictureArrayList);
        listView.setAdapter(lazyAdapter);
        listView.setOnItemClickListener(onClickListItem);
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String url = pictureArrayList.get(arg2).getUrl();
            String id = pictureArrayList.get(arg2).getId();
            Intent pictureViewIntent = new Intent(PicturesActivity
                    .this, PictureViewActivity.class);
            pictureViewIntent.putExtra(Constants.TAG_URL, url);
            pictureViewIntent.putExtra(Constants.TAG_ID, id);
            startActivity(pictureViewIntent);
        }
    };
}