package com.stm.dialog.story.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stm.R;
import com.stm.dialog.story.presenter.StoryDialogPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */


public class StoryDialogToOthersAdapter extends RecyclerView.Adapter<StoryDialogToOthersAdapter.StoryDialogToOthersViewHolder> {
    private StoryDialogPresenter storyDialogPresenter;
    private ArrayList<String> messages;
    private Context context;
    private int layout;

    public StoryDialogToOthersAdapter(StoryDialogPresenter storyDialogPresenter, ArrayList<String> messages, Context context, int layout) {
        this.storyDialogPresenter = storyDialogPresenter;
        this.messages = messages;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public StoryDialogToOthersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StoryDialogToOthersViewHolder storyDialogToOthersViewHolder = new StoryDialogToOthersViewHolder(storyDialogPresenter, messages, LayoutInflater.from(context).inflate(layout, parent, false));
        return storyDialogToOthersViewHolder;
    }

    @Override
    public void onBindViewHolder(StoryDialogToOthersViewHolder holder, int position) {
        String message = messages.get(position);
        holder.tv_storydialog.setText(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class StoryDialogToOthersViewHolder extends RecyclerView.ViewHolder {
        private StoryDialogPresenter storyDialogPresenter;
        private ArrayList<String> messages;

        @BindView(R.id.tv_storydialog)
        TextView tv_storydialog;

        public StoryDialogToOthersViewHolder(StoryDialogPresenter storyDialogPresenter, ArrayList<String> messages, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.storyDialogPresenter = storyDialogPresenter;
            this.messages = messages;
        }

        @OnClick(R.id.ll_storydialog)
        public void onClickStoryDialogToOthersLayout(){
            int position = getAdapterPosition();
            storyDialogPresenter.onClickStoryDialogToOthersLayout(position);
        }
    }
}