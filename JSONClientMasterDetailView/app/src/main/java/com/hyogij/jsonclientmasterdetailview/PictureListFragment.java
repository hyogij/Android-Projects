package com.hyogij.jsonclientmasterdetailview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyogij.jsonclientmasterdetailview.json.Picture;
import com.hyogij.jsonclientmasterdetailview.adapter.PictureItemRecycleViewAdapter;
import com.hyogij.jsonclientmasterdetailview.util.Utils;
import com.hyogij.jsonclientmasterdetailview.volley.VolleyHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * A fragment representing a list of Pictures.
 */
public class PictureListFragment extends Fragment {
    private static final String CLASS_NAME = PictureListFragment.class
            .getCanonicalName();

    private ArrayAdapter<Picture> arrayAdapter = null;
    private ArrayList<Picture> arrayList = null;
    private PictureItemRecycleViewAdapter adapter = null;

    private View recyclerView = null;
    private EditText editSearch = null;

    private StringBuilder url = null;
    private StringRequest stringRequest = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.picture_list_fragment,
                container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.picture_list);

        if (getArguments().containsKey(Constants.TAG_ALBUMID)) {
            String albumId = getArguments().getString(Constants.TAG_ALBUMID);
            url = new StringBuilder(Constants.PICTURE_REQUEST_URL);
            url.append(albumId);

            createStringRequest();
            requestJSON();

            // Search text in the listview
            editSearch = (EditText) rootView.findViewById(R.id.search);
        }

        return rootView;
    }

    private void requestJSON() {
        Utils.showProgresDialog(getActivity());
        VolleyHelper.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void createStringRequest() {
        // Request a string response from the provided URL
        stringRequest = new StringRequest(Request.Method.GET, url
                .toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgresDialog();

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Picture[] array = gson.fromJson(response, Picture[].class);
                        arrayList = new ArrayList<Picture>(Arrays.asList(array));
                        onRefreshList();
                        addSearchFilter();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.hideProgresDialog();

                Utils.showToast(getActivity(), getString(R
                        .string.json_error));
            }
        });
    }

    private void onRefreshList() {
        adapter = new PictureItemRecycleViewAdapter(getActivity(), arrayList,
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
