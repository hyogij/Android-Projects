package com.hyogij.jsonclientmasterdetailview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * A fragment representing a list of Comments.
 */
public class CommentListFragment extends Fragment {
    private static final String CLASS_NAME = CommentListFragment.class
            .getCanonicalName();

    private JsonRequestHelper jsonRequestHelper = null;

    private ArrayAdapter<Comment> arrayAdapter = null;
    private ArrayList<Comment> arrayList = null;
    private CommentItemRecycleViewAdapter adapter = null;

    private View recyclerView = null;
    private EditText editSearch = null;

    private StringBuilder url = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.comment_list_fragment,
                container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.comment_list);

        if (getArguments().containsKey(Constants.TAG_POSTID)) {
            String postId = getArguments().getString(Constants.TAG_POSTID);
            url = new StringBuilder(Constants.COMMENT_REQUEST_URL);
            url.append(postId);

            requestJSON();

            // Search text in the listview
            editSearch = (EditText) rootView.findViewById(R.id.search);
        }

        return rootView;
    }

    private void requestJSON() {
        jsonRequestHelper = new JsonRequestHelper(getActivity(), handler, url
                .toString
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
                    break;
                case 1:
                    Utils.showToast(getContext(), getString(R
                            .string.json_error));
                default:
                    break;
            }
        }
    };

    private void onRefreshList() {
        // Initialize and set the list adapter
        adapter = new CommentItemRecycleViewAdapter(getContext(), arrayList,
                false);
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
