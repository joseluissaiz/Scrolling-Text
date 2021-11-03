package com.adsys.scrollingtext;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ConvertibleEditText article;
    private Button              changeText;
    private Button              dismissChanges;
    private RecyclerView        commentSection;
    private CommentAdapter      commentAdapter;
    private TextInputLayout     comment;
    private Button              sendComment;
    private List<Comment>       commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        article = findViewById(R.id.article);
        article.loadFromPreferences();

        changeText = findViewById(R.id.change_text);
        changeText.setOnClickListener(view -> {
            if (article.isInEditMode()) {
                article.setEditMode(false);
                changeText.setText("EDIT");
                article.save();
                showMsg("Saved!");
            } else {
                article.setEditMode(true);
                changeText.setText("SAVE");
                dismissChanges.setVisibility(View.VISIBLE);
                showMsg("You can now edit this article");
            }
        });

        dismissChanges = findViewById(R.id.dismiss_changes);
        dismissChanges.setOnClickListener(view -> {
            article.setEditMode(false);
            article.loadFromPreferences();
            changeText.setText("EDIT");
            dismissChanges.setVisibility(View.GONE);
            showMsg("Changes weren't saved!");
        });

        loadComments();

        commentSection = findViewById(R.id.comment_section);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        commentSection.setLayoutManager(layoutManager);
        commentAdapter = new CommentAdapter(commentList);
        commentSection.setAdapter(commentAdapter);

        comment = findViewById(R.id.comment_text);
        comment.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                publishComment();
                return true;
            }
            return false;
        });

    }

    private void publishComment() {
        Comment comment = new Comment(String.valueOf(this.comment.getEditText().getText()), this);
        comment.save();
        commentList.add(comment);
        commentAdapter.notifyItemInserted(commentList.size() - (comment.getId()+1));
    }

    private void loadComments() {
        SharedPreferences sp;
        commentList = new ArrayList<>();
        boolean haveComments = true;
        int id = 1;
        while (haveComments) {
            sp = getBaseContext().getSharedPreferences("COMMENT-" + id, Context.MODE_PRIVATE);
            String comment = sp.getString("COMMENT-" + id, null);
            if (comment == null || comment.equals("")) {
                haveComments = false;
            } else {
                commentList.add(new Comment(comment, this));
            }
            id++;
        }
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}