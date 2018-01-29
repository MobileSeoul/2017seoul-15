package com.stm.main.fragment.main.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.main.fragment.main.base.presenter.MainFragmentPresenter;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-29.
 */

public class Best5StoryAdapter extends RecyclerView.Adapter<Best5StoryAdapter.Best5StoryViewHolder> {
    private MainFragmentPresenter mainFragmentPresenter;
    private List<Story> best5stories;
    private Context context;
    private int layout;

    public Best5StoryAdapter(MainFragmentPresenter mainFragmentPresenter, List<Story> best5stories, Context context, int layout) {
        this.mainFragmentPresenter = mainFragmentPresenter;
        this.best5stories = best5stories;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public Best5StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Best5StoryViewHolder best5StoryViewHolder = new Best5StoryViewHolder(mainFragmentPresenter, best5stories, LayoutInflater.from(context).inflate(layout, parent, false));
        return best5StoryViewHolder;
    }

    @Override
    public void onBindViewHolder(Best5StoryViewHolder holder, int position) {
        Story story = best5stories.get(position);
        User storyUser = story.getUser();
        String name = storyUser.getName();
        String avatar = storyUser.getAvatar();
        String content = story.getContent();
        int likeCount = story.getLikeCount();

        File file = story.getFiles().get(0);
        String photo = file.getName() + "." + file.getExt();
        String likeContent = "좋아요 " + likeCount + "개";

        holder.tv_storyrank_username.setText(name);
        holder.tv_storyrank_content.setText(content);
        holder.tv_storyrank_likecontent.setText(likeContent);

        Glide.with(context).load(holder.userAvatarUrl + avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_storyrank_useravatar);
        Glide.with(context).load(holder.storyImageUrl + photo).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_storyrank_photo);
    }

    @Override
    public int getItemCount() {
        return best5stories.size();
    }

    public static class Best5StoryViewHolder extends RecyclerView.ViewHolder implements HashTagHelper.OnHashTagClickListener {
        private MainFragmentPresenter mainFragmentPresenter;
        private List<Story> best5stories;
        private HashTagHelper hashTagHelper;

        @BindView(R.id.iv_storyrank_photo)
        ImageView iv_storyrank_photo;

        @BindView(R.id.iv_storyrank_useravatar)
        ImageView iv_storyrank_useravatar;

        @BindView(R.id.tv_storyrank_username)
        TextView tv_storyrank_username;

        @BindView(R.id.tv_storyrank_content)
        TextView tv_storyrank_content;


        @BindView(R.id.tv_storyrank_likecontent)
        TextView tv_storyrank_likecontent;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        @BindColor(R.color.pointColor)
        int pointColor;

        public Best5StoryViewHolder(MainFragmentPresenter mainFragmentPresenter, List<Story> best5stories, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mainFragmentPresenter = mainFragmentPresenter;
            this.best5stories = best5stories;

            this.hashTagHelper = HashTagHelper.Creator.create(pointColor, this);
            this.hashTagHelper.handle(tv_storyrank_content);
        }

        @OnClick(R.id.ll_storyrank_info)
        public void onClickStory() {
            int position = getAdapterPosition();
            Story story = best5stories.get(position);
            mainFragmentPresenter.onClickBestStory(story, position);
        }

        @Override
        public void onHashTagClicked(String hashTag) {
            mainFragmentPresenter.onHashTagClicked(hashTag);
        }

        @OnClick({R.id.tv_storyrank_username, R.id.iv_storyrank_useravatar})
        public void onClickUser() {
            int position = getAdapterPosition();
            Story story = best5stories.get(position);
            User user = story.getUser();
            mainFragmentPresenter.onClickBestUser(user, position);
        }
    }
}