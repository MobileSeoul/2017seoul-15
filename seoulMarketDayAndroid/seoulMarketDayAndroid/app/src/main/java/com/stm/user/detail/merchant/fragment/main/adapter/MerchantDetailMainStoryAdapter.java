package com.stm.user.detail.merchant.fragment.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
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
import com.stm.story.detail.activity.StoryDetailActivity;
import com.stm.user.detail.merchant.fragment.main.presenter.MerchantDetailMainPresenter;
import com.viewpagerindicator.CirclePageIndicator;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * Created by ㅇㅇ on 2017-07-24.
 */

public class MerchantDetailMainStoryAdapter extends RecyclerView.Adapter<MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder> {
    private MerchantDetailMainPresenter merchantDetailMainPresenter;
    private MerchantDetailMainFileAdapter merchantDetailMainFileAdapter;

    private List<Story> stories;
    private Context context;
    private int layout;

    public MerchantDetailMainStoryAdapter(MerchantDetailMainPresenter merchantDetailMainPresenter, List<Story> stories, Context context, int layout) {
        this.merchantDetailMainPresenter = merchantDetailMainPresenter;
        this.stories = stories;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MerchantDetailStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MerchantDetailStoryViewHolder merchantDetailStoryViewHolder = new MerchantDetailStoryViewHolder(merchantDetailMainPresenter, stories, LayoutInflater.from(context).inflate(layout, parent, false));
        return merchantDetailStoryViewHolder;
    }

    @Override
    public void onBindViewHolder(MerchantDetailStoryViewHolder holder, int position) {
        Story story = stories.get(position);
        String dateTime = story.getCreatedAt();
        String content = story.getContent();
        boolean isLikeChecked = story.getLikeChecked();
        List<File> files = story.getFiles();
        int fileSize = files.size();
        int likeCount = story.getLikeCount();
        int commentCount = story.getCommentCount();

        User user = story.getUser();
        String name = user.getName();
        String avatar = user.getAvatar();

        holder.tv_merchantdetailmain_storycommentcount.setText(commentCount + "");
        holder.tv_merchantdetailmain_storyname.setText(name);
        holder.tv_merchantdetailmain_storycontent.setText(content);
        holder.tv_merchantdetailmain_storydate.setText(CalculateDateUtil.getCalculateDateByDateTime(dateTime));
        holder.tv_merchantdetailmain_storylikecount.setText(likeCount + "");
        holder.tv_merchantdetailmain_storytotal.setText(String.valueOf(fileSize));
        holder.cb_merchantdetailmain_storylike.setChecked(isLikeChecked);
        story.setFirstLikeChecked(true);
        Glide.with(context).load(holder.userAvatarUrl + avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_merchantdetailmain_storyavatar);

        Linkify.addLinks(holder.tv_merchantdetailmain_storycontent, Linkify.WEB_URLS);


        merchantDetailMainPresenter.setFileAdapterItem(holder, files);
    }

    public void setMerchantDetailMainFileAdapterItem(MerchantDetailStoryViewHolder holder, List<File> files) {
        this.merchantDetailMainFileAdapter = new MerchantDetailMainFileAdapter(merchantDetailMainPresenter, files, context, R.layout.item_merchantdetailmain_file);
        holder.vp_merchantdetailmain_storymedia.setAdapter(merchantDetailMainFileAdapter);
        holder.cpi_merchantdetailmain_story.setViewPager(holder.vp_merchantdetailmain_storymedia);
        holder.ll_merchantdetailmain_storyposition.bringToFront();
    }

    public void goneFile(MerchantDetailStoryViewHolder holder) {
        holder.fl_merchantdetailmain_storymedia.setVisibility(View.GONE);
    }

    public void gonePosition(MerchantDetailStoryViewHolder holder) {
        holder.ll_merchantdetailmain_storyposition.setVisibility(View.GONE);
    }

    public void goneIndicator(MerchantDetailStoryViewHolder holder) {
        holder.cpi_merchantdetailmain_story.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void showFile(MerchantDetailStoryViewHolder holder) {
        holder.fl_merchantdetailmain_storymedia.setVisibility(View.VISIBLE);
    }

    public static class MerchantDetailStoryViewHolder extends RecyclerView.ViewHolder implements HashTagHelper.OnHashTagClickListener {
        private MerchantDetailMainPresenter merchantDetailMainPresenter;
        private List<Story> stories;
        private HashTagHelper hashTagHelper;

        @BindView(R.id.iv_merchantdetailmain_storyavatar)
        ImageView iv_merchantdetailmain_storyavatar;

        @BindView(R.id.tv_merchantdetailmain_storyname)
        TextView tv_merchantdetailmain_storyname;

        @BindView(R.id.tv_merchantdetailmain_storydate)
        TextView tv_merchantdetailmain_storydate;

        @BindView(R.id.tv_merchantdetailmain_storycontent)
        TextView tv_merchantdetailmain_storycontent;

        @BindView(R.id.tv_merchantdetailmain_storylikecount)
        TextView tv_merchantdetailmain_storylikecount;

        @BindView(R.id.vp_merchantdetailmain_storymedia)
        ViewPager vp_merchantdetailmain_storymedia;

        @BindView(R.id.cpi_merchantdetailmain_story)
        CirclePageIndicator cpi_merchantdetailmain_story;

        @BindView(R.id.ll_merchantdetailmain_storyposition)
        LinearLayout ll_merchantdetailmain_storyposition;

        @BindView(R.id.tv_merchantdetailmain_storyposition)
        TextView tv_merchantdetailmain_storyposition;

        @BindView(R.id.tv_merchantdetailmain_storytotal)
        TextView tv_merchantdetailmain_storytotal;

        @BindView(R.id.tv_merchantdetailmain_storycommentcount)
        TextView tv_merchantdetailmain_storycommentcount;

        @BindView(R.id.fl_merchantdetailmain_storymedia)
        FrameLayout fl_merchantdetailmain_storymedia;

        @BindView(R.id.cb_merchantdetailmain_storylike)
        CheckBox cb_merchantdetailmain_storylike;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        @BindColor(R.color.pointColor)
        int pointColor;

        @BindColor(R.color.colorPrimaryDark)
        int colorPrimaryDark;


        public MerchantDetailStoryViewHolder(MerchantDetailMainPresenter merchantDetailMainPresenter, List<Story> stories, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.merchantDetailMainPresenter = merchantDetailMainPresenter;
            this.stories = stories;

            this.hashTagHelper = HashTagHelper.Creator.create(pointColor, this);
            this.hashTagHelper.handle(tv_merchantdetailmain_storycontent);
        }

        @OnClick({R.id.ib_merchantdetailmain_storycomment, R.id.tv_merchantdetailmain_storymorecomment})
        public void onClickComment() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            merchantDetailMainPresenter.onClickComment(story, position);
        }

        @OnPageChange(R.id.vp_merchantdetailmain_storymedia)
        public void onPageSelected(int position) {
            String positionMessage = (position + 1) + "/";
            tv_merchantdetailmain_storyposition.setText(positionMessage);
        }

        @OnCheckedChanged(R.id.cb_merchantdetailmain_storylike)
        public void onLikeChecked(boolean checked) {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            Boolean isFirstLikeChecked = story.getFirstLikeChecked();

            if (isFirstLikeChecked) {
                merchantDetailMainPresenter.onLikeChecked(position, story, checked);
            }
        }

        @OnClick(R.id.ib_merchantdetailstory_menu)
        public void onClickMenu() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            merchantDetailMainPresenter.onClickMenu(story, position);
        }

        @Override
        public void onHashTagClicked(String hashTag) {
            merchantDetailMainPresenter.onHashTagClicked(hashTag);
        }
    }
}
