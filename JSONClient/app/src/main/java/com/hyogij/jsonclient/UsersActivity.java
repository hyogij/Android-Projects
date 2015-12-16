package com.hyogij.jsonclient;

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
import com.hyogij.jsonclient.Adapters.UserAdapter;
import com.hyogij.jsonclient.Const.Constants;
import com.hyogij.jsonclient.JsonDatas.User;
import com.hyogij.jsonclient.JsonRequestUtils.JsonRequestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class UsersActivity extends Activity {
    private static final String CLASS_NAME = UsersActivity.class
            .getCanonicalName();

    private JsonRequestHelper jsonRequestHelper = null;

    private ArrayAdapter<User> arrayAdapter = null;
    private ArrayList<User> userArrayList = null;
    private UserAdapter userAdapter = null;

    private ListView listView = null;
    private EditText editSearch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_activity);

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
                userAdapter.filter(text);
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
        jsonRequestHelper = new JsonRequestHelper(this, handler, Constants.USER_REQUEST_URL);
    }

    // Handler to wait receiving json data
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    User[] userArray = gson.fromJson(jsonRequestHelper.getJsonData(), User[].class);
                    userArrayList = new ArrayList<User>(Arrays.asList(userArray));
                    onRefreshList();
                    break;
                default:
                    break;
            }
        }
    };

    private void onRefreshList() {
        userAdapter = new UserAdapter(this, R.layout.user_item, userArrayList);
        listView.setAdapter(userAdapter);
        listView.setOnItemClickListener(onClickListItem);
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String userId = userArrayList.get(arg2).getId();
            Intent albumIntent = new Intent(UsersActivity
                    .this, AlbumsActivity.class);
            albumIntent.putExtra(Constants.TAG_USERID, userId);
            startActivity(albumIntent);
        }
    };
}
