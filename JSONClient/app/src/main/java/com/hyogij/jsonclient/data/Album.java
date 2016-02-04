package com.hyogij.jsonclient.data;

/**
 * Created by hyogij on 15. 12. 15..
 */
public class Album {
    private String userId = null;
    private String id = null;
    private String title = null;

    public Album(String userId, String id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        // Ignore other fields
        return title;
    }
}
