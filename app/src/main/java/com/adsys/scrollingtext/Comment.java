package com.adsys.scrollingtext;

import android.content.Context;
import android.content.SharedPreferences;

public class Comment {
    private static int     idCount = 0;
    private final  int     id;
    private final  Context context;
    private final  String  TAG;
    private        String  text;

    public Comment(String text, Context context) {
        this.id = idCount;
        idCount++;
        this.TAG = "COMMENT-" + id;
        this.text = text;
        this.context = context;
    }

    public void save() {
        String text = String.valueOf(this.getText());
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit().putString(TAG, text);
        editor.apply();
    }

    public void loadFromPreferences() {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String text = sp.getString(TAG, null);
        if (text == null || text.equals("")) {
            this.save();
            text = sp.getString(TAG, null);
        }
        this.setText(text);
    }

    public int getId() { return id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Context getContext() { return context; }
}
