package com.hyogij.jsonclientmasterdetailview;

/**
 * A class for constant variables.
 */
public class Constants {
    public static final String URL_PREFIX = "http://jsonplaceholder.typicode" +
            ".com";
    public static final String USER_REQUEST_URL = URL_PREFIX + "/users";
    public static final String ALBUM_REQUEST_URL = URL_PREFIX +
            "/albums?userId=";
    public static final String PICTURE_REQUEST_URL = URL_PREFIX +
            "/photos?albumId=";
    public static final String POST_REQUEST_URL = URL_PREFIX + "/posts?userId=";
    public static final String COMMENT_REQUEST_URL = URL_PREFIX +
            "/comments?postId=";

    public static final String TAG_USERID = "USERID";
    public static final String TAG_ALBUMID = "ALBUMID";
    public static final String TAG_POSTID = "POSTID";
    public static final String TAG_URL = "URL";
    public static final String TAG_ID = "ID";
}
