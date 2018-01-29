package com.stm.market.fragment.story.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
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
import com.stm.market.fragment.story.presenter.MarketStoryPresenter;
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

public class MarketStoryAdapter extends RecyclerView.Adapter<MarketStoryAdapter.MarketStoryViewHolder> {
    private MarketStoryPresenter marketStoryPresenter;
    private List<Story> stories;
    private Context context;
    private int layout;
    private MarketStoryFileAdapter marketStoryFileAdapter;

    public MarketStoryAdapter(MarketStoryPresenter marketStoryPresenter, List<Story> stories, Context context, int layout) {
        this.marketStoryPresenter = marketStoryPresenter;
        this.stories = stories;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MarketStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MarketStoryViewHolder marketStoryViewHolder = new MarketStoryViewHolder(marketStoryPresenter, stories, LayoutInflater.from(context).inflate(layout, parent, false));
        return marketStoryViewHolder;
    }

    @Override
    public void onBindViewHolder(MarketStoryViewHolder holder, int position) {
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

        holder.tv_marketstory_name.setText(name);
        holder.tv_marketstory_date.setText(CalculateDateUtil.getCalculateDateByDateTime(createdAt));
        holder.tv_marketstory_content.setText(content);
        holder.tv_marketstory_commentcount.setText(String.valueOf(commentCount));
        holder.tv_marketstory_likecount.setText(String.valueOf(likeCount));
        holder.tv_marketstory_total.setText(String.valueOf(fileSize));
        Glide.with(context).load(holder.userAvatarUrl + avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_marketstory_avatar);

        holder.cb_marketstory_like.setChecked(isLikeChecked);
        story.setFirstLikeChecked(true);

        Linkify.addLinks(holder.tv_marketstory_content, Linkify.WEB_URLS);

        marketStoryPresenter.setFileAdapterItem(holder, files);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void showPosition(MarketStoryViewHolder holder) {
        holder.ll_marketstory_position.setVisibility(View.VISIBLE);
    }

    public void showIndicator(MarketStoryViewHolder holder) {
        holder.cpi_marketstory.setVisibility(View.VISIBLE);
    }

    public void gonePosition(MarketStoryViewHolder holder) {
        holder.ll_marketstory_position.setVisibility(View.GONE);
    }

    public void goneIndicator(MarketStoryViewHolder holder) {
        holder.cpi_marketstory.setVisibility(View.GONE);
    }

    public void goneFile(MarketStoryViewHolder holder) {
        holder.fl_marketstory_file.setVisibility(View.GONE);
    }

    public void setMarketStoryFileAdapterItem(MarketStoryViewHolder holder, List<File> files) {
        marketStoryFileAdapter = new MarketStoryFileAdapter(marketStoryPresenter, files, context, R.layout.item_marketstory_file);
        holder.vp_marketstory_file.setAdapter(marketStoryFileAdapter);
    }

    public void setMarketStoryAdapterLikeUnchecked(MarketStoryViewHolder holder) {
        holder.cb_marketstory_like.setChecked(false);
    }

    public void showFile(MarketStoryViewHolder holder) {
        holder.fl_marketstory_file.setVisibility(View.VISIBLE);
    }

    public static class MarketStoryViewHolder extends RecyclerView.ViewHolder implements HashTagHelper.OnHashTagClickListener {
        private MarketStoryPresenter marketStoryPresenter;
        private List<Story> stories;
        private HashTagHelper hashTagHelper;

        @BindView(R.id.iv_marketstory_avatar)
        ImageView iv_marketstory_avatar;

        @BindView(R.id.tv_marketstory_name)
        TextView tv_marketstory_name;

        @BindView(R.id.tv_marketstory_date)
        TextView tv_marketstory_date;

        @BindView(R.id.tv_marketstory_content)
        TextView tv_marketstory_content;

        @BindView(R.id.tv_marketstory_likecount)
        TextView tv_marketstory_likecount;

        @BindView(R.id.vp_marketstory_file)
        ViewPager vp_marketstory_file;

        @BindView(R.id.cpi_marketstory)
        CirclePageIndicator cpi_marketstory;

        @BindView(R.id.ll_marketstory_position)
        LinearLayout ll_marketstory_position;

        @BindView(R.id.tv_marketstory_position)
        TextView tv_marketstory_position;

        @BindView(R.id.tv_marketstory_total)
        TextView tv_marketstory_total;

        @BindView(R.id.tv_marketstory_commentcount)
        TextView tv_marketstory_commentcount;

        @BindView(R.id.ib_marketstory_comment)
        ImageButton ib_marketstory_comment;

        @BindView(R.id.tv_marketstory_morecomment)
        TextView tv_marketstory_morecomment;

        @BindView(R.id.fl_marketstory_file)
        FrameLayout fl_marketstory_file;

        @BindView(R.id.cb_marketstory_like)
        CheckBox cb_marketstory_like;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        @BindColor(R.color.pointColor)
        int pointColor;

        public MarketStoryViewHolder(MarketStoryPresenter marketStoryPresenter, List<Story> stories, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.marketStoryPresenter = marketStoryPresenter;
            this.stories = stories;
            this.hashTagHelper = HashTagHelper.Creator.create(pointColor, this);
            this.hashTagHelper.handle(tv_marketstory_content);
        }

        @Override
        public void onHashTagClicked(String hashTag) {
            marketStoryPresenter.onHashTagClicked(hashTag);
        }

        @OnClick({R.id.tv_marketstory_morecomment, R.id.ib_marketstory_comment, R.id.tv_marketstory_commentcount})
        public void onClickComment() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            marketStoryPresenter.onClickComment(story, position);
        }

        @OnPageChange(R.id.vp_marketstory_file)
        public void onPageSelected(int position) {
            String positionMessage = (position + 1) + "/";
            tv_marketstory_position.setText(positionMessage);
        }

        @OnCheckedChanged(R.id.cb_marketstory_like)
        public void onLikeChecked(boolean checked) {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            Boolean isFirstLikeChecked = story.getFirstLikeChecked();

            if (isFirstLikeChecked) {
                marketStoryPresenter.onLikeChecked(this, position, story, checked);
            }
        }

        @OnClick(R.id.ib_marketstory_menu)
        public void onClickMenu() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            marketStoryPresenter.onClickMenu(story, position);
        }

        @OnClick({R.id.iv_marketstory_avatar, R.id.tv_marketstory_name})
        public void onClickAvatar() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            User storyUser = story.getUser();
            marketStoryPresenter.onClickAvatar(storyUser);
        }
    }
}
