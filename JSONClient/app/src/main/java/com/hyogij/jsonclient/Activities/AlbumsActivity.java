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
import com.hyogij.jsonclient.Adapters.AlbumAdapter;
import com.hyogij.jsonclient.Const.Constants;
import com.hyogij.jsonclient.JsonDatas.Album;
import com.hyogij.jsonclient.JsonRequestUtils.JsonRequestHelper;
import com.hyogij.jsonclient.R;
import com.hyogij.jsonclient.StringUtils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class AlbumsActivity extends Activity {
    private static final String CLASS_NAME = AlbumsActivity.class
            .getCanonicalName();

    private JsonRequestHelper jsonRequestHelper = null;

    private ArrayAdapter<Album> arrayAdapter = null;
    private ArrayList<Album> albumArrayList = null;
    private AlbumAdapter albumAdapter = null;

    private ListView listView = null;
    private EditText editSearch = null;

    private StringBuilder url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_activity);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(Constants.TAG_USERID);
        url = new StringBuilder(Constants.ALBUM_REQUEST_URL);
        url.append(userId);

        // Change an activity name
        setTitle(Utils.getActvityTitle(getString(R.string.albums_activity), Constants
                .TAG_USERID, userId));

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
                albumAdapter.filter(text);
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
                    Album[] userArray = gson.fromJson(jsonRequestHelper.getJsonData(), Album[].class);
                    albumArrayList = new ArrayList<Album>(Arrays.asList(userArray));
                    onRefreshList();
                    break;
                default:
                    break;
            }
        }
    };

    private void onRefreshList() {
        albumAdapter = new AlbumAdapter(this, R.layout.album_item, albumArrayList);
        listView.setAdapter(albumAdapter);
        listView.setOnItemClickListener(onClickListItem);
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String albumId = albumArrayList.get(arg2).getId();
            Intent albumIntent = new Intent(AlbumsActivity
                    .this, PicturesActivity.class);
            albumIntent.putExtra(Constants.TAG_ALBUMID, albumId);
            startActivity(albumIntent);
        }
    };
}