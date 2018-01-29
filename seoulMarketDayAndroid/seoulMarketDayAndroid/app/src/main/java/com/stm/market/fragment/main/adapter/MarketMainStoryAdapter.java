package com.stm.market.fragment.main.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.util.CalculateDateUtil;
import com.stm.market.fragment.main.presenter.MarketMainPresenter;
import com.viewpagerindicator.CirclePageIndicator;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketMainStoryAdapter extends RecyclerView.Adapter<MarketMainStoryAdapter.MarketMainStoryViewHolder> {
    private MarketMainPresenter marketMainPresenter;
    private List<Story> stories;
    private Context context;
    private int layout;
    private MarketMainFileAdapter marketMainFileAdapter;

    public MarketMainStoryAdapter(MarketMainPresenter marketMainPresenter, List<Story> stories, Context context, int layout) {
        this.marketMainPresenter = marketMainPresenter;
        this.stories = stories;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MarketMainStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MarketMainStoryViewHolder marketMainStoryViewHolder = new MarketMainStoryViewHolder(marketMainPresenter, stories, LayoutInflater.from(context).inflate(layout, parent, false));
        return marketMainStoryViewHolder;
    }

    @Override
    public void onBindViewHolder(MarketMainStoryViewHolder holder, int position) {
        Story story = stories.get(position);
        String createdAt = story.getCreatedAt();
        String content = story.getContent();
        int likeCount = story.getLikeCount();
        int commentCount = story.getCommentCount();
        List<File> files = story.getFiles();
        int fileSize = files.size();
        boolean isLikeChecked = story.getLikeChecked();

        User user = story.getUser();
        String avatar = user.getAvatar();
        String name = user.getName();

        holder.tv_marketmain_storyname.setText(name);
        holder.tv_marketmain_storydate.setText(CalculateDateUtil.getCalculateDateByDateTime(createdAt));
        holder.tv_marketmain_storycontent.setText(content);
        holder.tv_marketmain_storycommentcount.setText(String.valueOf(commentCount));
        holder.tv_marketmain_storylikecount.setText(String.valueOf(likeCount));
        holder.tv_marketmain_storytotal.setText(String.valueOf(fileSize));
        Glide.with(context).load(holder.userAvatarUrl + avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_marketmain_storyavatar);

        holder.cb_marketmain_storylike.setChecked(isLikeChecked);
        story.setFirstLikeChecked(true);

        Linkify.addLinks(holder.tv_marketmain_storycontent, Linkify.WEB_URLS);

        marketMainPresenter.setFileAdapterItem(holder, files);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void setMarketMainFileAdapterItem(MarketMainStoryViewHolder holder, List<File> files) {
        marketMainFileAdapter = new MarketMainFileAdapter(marketMainPresenter, files, context, R.layout.item_marketmain_file);
        holder.vp_marketmain_storyfile.setAdapter(marketMainFileAdapter);
    }

    public void goneFile(MarketMainStoryViewHolder holder) {
        holder.fl_marketmain_storyfile.setVisibility(View.GONE);
    }

    public void gonePosition(MarketMainStoryViewHolder holder) {
        holder.ll_marketmain_storyposition.setVisibility(View.GONE);
    }

    public void goneIndicator(MarketMainStoryViewHolder holder) {
        holder.cpi_marketmain_story.setVisibility(View.GONE);
    }

    public void showPosition(MarketMainStoryViewHolder holder) {
        holder.tv_marketmain_storyposition.setVisibility(View.VISIBLE);
        holder.ll_marketmain_storyposition.bringToFront();
    }

    public void showFile(MarketMainStoryViewHolder holder) {
        holder.fl_marketmain_storyfile.setVisibility(View.VISIBLE);
    }

    public void showIndicator(MarketMainStoryViewHolder holder) {
        holder.cpi_marketmain_story.setVisibility(View.VISIBLE);
    }

    public void setMarketMainStoryAdapterLikeUnchecked(MarketMainStoryViewHolder holder) {
        holder.cb_marketmain_storylike.setChecked(false);
    }

    public static class MarketMainStoryViewHolder extends RecyclerView.ViewHolder implements HashTagHelper.OnHashTagClickListener {
        private MarketMainPresenter marketMainPresenter;
        private List<Story> stories;
        private HashTagHelper hashTagHelper;

        @BindView(R.id.iv_marketmain_storyavatar)
        ImageView iv_marketmain_storyavatar;

        @BindView(R.id.tv_marketmain_storyname)
        TextView tv_marketmain_storyname;

        @BindView(R.id.tv_marketmain_storydate)
        TextView tv_marketmain_storydate;

        @BindView(R.id.tv_marketmain_storycontent)
        TextView tv_marketmain_storycontent;

        @BindView(R.id.tv_marketmain_storylikecount)
        TextView tv_marketmain_storylikecount;

        @BindView(R.id.vp_marketmain_storyfile)
        ViewPager vp_marketmain_storyfile;

        @BindView(R.id.cpi_marketmain_story)
        CirclePageIndicator cpi_marketmain_story;

        @BindView(R.id.ll_marketmain_storyposition)
        LinearLayout ll_marketmain_storyposition;

        @BindView(R.id.tv_marketmain_storyposition)
        TextView tv_marketmain_storyposition;

        @BindView(R.id.tv_marketmain_storytotal)
        TextView tv_marketmain_storytotal;

        @BindView(R.id.tv_marketmain_storycommentcount)
        TextView tv_marketmain_storycommentcount;

        @BindView(R.id.ib_marketmain_storycomment)
        ImageButton ib_marketmain_storycomment;

        @BindView(R.id.tv_marketmain_storymorecomment)
        TextView tv_marketmain_storymorecomment;

        @BindView(R.id.fl_marketmain_storyfile)
        FrameLayout fl_marketmain_storyfile;

        @BindView(R.id.cb_marketmain_storylike)
        CheckBox cb_marketmain_storylike;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        @BindColor(R.color.pointColor)
        int pointColor;

        public MarketMainStoryViewHolder(MarketMainPresenter marketMainPresenter, List<Story> stories, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.marketMainPresenter = marketMainPresenter;
            this.stories = stories;
            this.hashTagHelper = HashTagHelper.Creator.create(pointColor, this);
            this.hashTagHelper.handle(tv_marketmain_storycontent);
        }

        @Override
        public void onHashTagClicked(String hashTag) {
            marketMainPresenter.onHashTagClicked(hashTag);
        }

        @OnClick({R.id.tv_marketmain_storymorecomment, R.id.ib_marketmain_storycomment, R.id.tv_marketmain_storycommentcount})
        public void onClickComment() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            marketMainPresenter.onClickComment(story, position);
        }

        @OnPageChange(R.id.vp_marketmain_storyfile)
        public void onPageSelected(int position) {
            String positionMessage = (position + 1) + "/";
            tv_marketmain_storyposition.setText(positionMessage);
        }

        @OnCheckedChanged(R.id.cb_marketmain_storylike)
        public void onLikeChecked(boolean checked) {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            Boolean isFirstLikeChecked = story.getFirstLikeChecked();

            if (isFirstLikeChecked) {
                marketMainPresenter.onLikeChecked(this, position, story, checked);
            }
        }

        @OnClick(R.id.ib_marketmain_menu)
        public void onClickMenu() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            marketMainPresenter.onClickMenu(story, position);
        }

        @OnClick({R.id.iv_marketmain_storyavatar, R.id.tv_marketmain_storyname})
        public void onClickAvatar() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            User storyUser = story.getUser();
            marketMainPresenter.onClickAvatar(storyUser);
        }
    }
}
