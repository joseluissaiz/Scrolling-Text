package com.adsys.scrollingtext;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter {
    private List<Comment> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;

        public ViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    public CommentAdapter(List<Comment> myDataSet) {
        mDataSet = myDataSet;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.comment_text);
        textView.setText((mDataSet.get(mDataSet.size() - (position+1)).getText()));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}
