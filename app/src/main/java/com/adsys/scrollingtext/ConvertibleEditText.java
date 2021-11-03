package com.adsys.scrollingtext;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

public class ConvertibleEditText extends AppCompatEditText {
    private String  TAG;
    private boolean editMode;

    public ConvertibleEditText(Context context) {
        super(context);
    }

    public ConvertibleEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ConvertibleEditText,
                0, 0);

        try {
            TAG = a.getString(R.styleable.ConvertibleEditText_TAG);
            editMode = a.getBoolean(R.styleable.ConvertibleEditText_edit_mode, false);
            setEditMode(editMode);
        } finally {
            a.recycle();
        }
    }

    public ConvertibleEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ConvertibleEditText(String TAG, Context context) {
        super(context);
        this.TAG = TAG;
        this.editMode = false;
        this.setEditMode(false);
    }

    public ConvertibleEditText(String TAG, EditText editText) {
        super(editText.getContext());
        this.TAG = TAG;
        this.editMode = false;
        this.setEditMode(false);
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        if (editMode) {
            this.setClickable(true);
            this.setLongClickable(true);
            this.setFocusableInTouchMode(true);
            this.requestFocus();
            this.invalidate();
        } else {
            this.setFocusable(false);
            this.setClickable(false);
            this.setLongClickable(false);
        }
    }

    public boolean isInEditMode() {
        return editMode;
    }

    public void save() {
        String text = String.valueOf(this.getText());
        SharedPreferences sp = getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit().putString(TAG, text);
        editor.apply();
    }

    public void loadFromPreferences() {
        SharedPreferences sp = getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String text = sp.getString(TAG, null);
        if (text == null || text.equals("")) {
            this.save();
            text = sp.getString(TAG, null);
        }
        this.setText(text);
    }

}
