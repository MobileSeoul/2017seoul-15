package com.stm.dialog.comment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stm.R;
import com.stm.dialog.comment.presenter.CommentDialogPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-16.
 */

public class CommentDialogToAuthorAdapter extends RecyclerView.Adapter<CommentDialogToAuthorAdapter.CommentDialogToAuthorViewHolder> {
    private CommentDialogPresenter commentDialogPresenter;
    private ArrayList<String> messages;
    private Context context;
    private int layout;

    public CommentDialogToAuthorAdapter(CommentDialogPresenter commentDialogPresenter, ArrayList<String> messages, Context context, int layout) {
        this.commentDialogPresenter = commentDialogPresenter;
        this.context = context;
        this.messages = messages;
        this.layout = layout;
    }

    @Override
    public CommentDialogToAuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentDialogToAuthorViewHolder commentDialogToAuthorViewHolder = new CommentDialogToAuthorViewHolder(commentDialogPresenter, LayoutInflater.from(context).inflate(layout, parent, false));
        return commentDialogToAuthorViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentDialogToAuthorViewHolder holder, int position) {
        String message = messages.get(position);
        holder.tv_commentdialog.setText(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class CommentDialogToAuthorViewHolder extends RecyclerView.ViewHolder {
        private CommentDialogPresenter commentDialogPresenter;

        @BindView(R.id.tv_commentdialog)
        TextView tv_commentdialog;

        public CommentDialogToAuthorViewHolder(CommentDialogPresenter commentDialogPresenter, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.commentDialogPresenter = commentDialogPresenter;
        }

        @OnClick(R.id.ll_commentdialog)
        public void onClickCommentDialogLayout() {
            int position = getAdapterPosition();
            commentDialogPresenter.onClickCommentDialogToAuthorLayout(position);
        }

    }
}
