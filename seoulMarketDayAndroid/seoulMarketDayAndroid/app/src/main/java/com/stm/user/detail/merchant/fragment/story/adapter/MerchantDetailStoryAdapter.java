package com.stm.user.detail.merchant.fragment.story.adapter;

import android.content.Context;
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
import com.stm.user.detail.merchant.fragment.story.presenter.MerchantDetailStoryPresenter;
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
import butterknife.Optional;

/**
 * Created by ㅇㅇ on 2017-07-24.
 */

public class MerchantDetailStoryAdapter extends RecyclerView.Adapter<MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder>{
    private MerchantDetailStoryPresenter merchantDetailStoryPresenter;
    private MerchantDetailStoryFileAdapter merchantDetailStoryFileAdapter;
    private List<Story> stories;
    private Context context;
    private int layout;

    public MerchantDetailStoryAdapter(MerchantDetailStoryPresenter merchantDetailStoryPresenter, List<Story> stories, Context context, int layout) {
        this.merchantDetailStoryPresenter = merchantDetailStoryPresenter;
        this.stories = stories;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MerchantDetailStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MerchantDetailStoryViewHolder merchantDetailStoryViewHolder = new MerchantDetailStoryViewHolder(merchantDetailStoryPresenter, stories, LayoutInflater.from(context).inflate(layout, parent, false));
        return merchantDetailStoryViewHolder;
    }

    @Override
    public void onBindViewHolder(MerchantDetailStoryViewHolder holder, int position) {
        Story story = stories.get(position);
        User user = story.getUser();
        String name = user.getName();
        String avatar = user.getAvatar();
        boolean isLikeChecked = story.getLikeChecked();
        String dateTime = story.getCreatedAt();
        String content = story.getContent();
        int likeCount = story.getLikeCount();
        int commentCount = story.getCommentCount();

        List<File> files = story.getFiles();
        int fileSize = files.size();

        holder.tv_merchantdetailstory_commentcount.setText(commentCount + "");
        holder.tv_merchantdetailstory_name.setText(name);
        holder.tv_merchantdetailstory_content.setText(content);
        holder.tv_merchantdetailstory_date.setText(CalculateDateUtil.getCalculateDateByDateTime(dateTime));
        holder.tv_merchantdetailstory_likecount.setText(likeCount + "");
        holder.tv_merchantdetailstory_total.setText(String.valueOf(fileSize));
        holder.cb_merchantdetailstory_like.setChecked(isLikeChecked);
        story.setFirstLikeChecked(true);
        Glide.with(context).load(holder.cloud_front_user_avatar + avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_merchantdetailstory_avatar);

        Linkify.addLinks(holder.tv_merchantdetailstory_content, Linkify.WEB_URLS);

        merchantDetailStoryPresenter.setMerchantDetailStoryFileAdapterItem(holder, files);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void setMerchantDetailStoryFileAdapterItem(MerchantDetailStoryViewHolder holder, List<File> files) {
        this.merchantDetailStoryFileAdapter = new MerchantDetailStoryFileAdapter(merchantDetailStoryPresenter, files, context);
        holder.vp_merchantdetailstory_media.setAdapter(merchantDetailStoryFileAdapter);
        holder.cpi_merchantdetailstory.setViewPager(holder.vp_merchantdetailstory_media);
        holder.ll_merchantdetailstory_position.bringToFront();
    }

    public void goneFile(MerchantDetailStoryViewHolder holder) {
        holder.fl_merchantdetailstory_media.setVisibility(View.GONE);
    }

    public void gonePosition(MerchantDetailStoryViewHolder holder) {
        holder.ll_merchantdetailstory_position.setVisibility(View.GONE);
    }

    public void goneIndicator(MerchantDetailStoryViewHolder holder) {
        holder.cpi_merchantdetailstory.setVisibility(View.GONE);
    }

    public void showFile(MerchantDetailStoryViewHolder holder) {
        holder.fl_merchantdetailstory_media.setVisibility(View.VISIBLE);
    }


    public static class MerchantDetailStoryViewHolder extends RecyclerView.ViewHolder implements HashTagHelper.OnHashTagClickListener{
        private MerchantDetailStoryPresenter merchantDetailStoryPresenter;
        private List<Story> stories;
        private HashTagHelper hashTagHelper;

        @BindView(R.id.iv_merchantdetailstory_avatar)
        ImageView iv_merchantdetailstory_avatar;

        @BindView(R.id.tv_merchantdetailstory_commentcount)
        TextView tv_merchantdetailstory_commentcount;

        @BindView(R.id.tv_merchantdetailstory_name)
        TextView tv_merchantdetailstory_name;

        @BindView(R.id.tv_merchantdetailstory_content)
        TextView tv_merchantdetailstory_content;

        @BindView(R.id.tv_merchantdetailstory_date)
        TextView tv_merchantdetailstory_date;

        @BindView(R.id.tv_merchantdetailstory_likecount)
        TextView tv_merchantdetailstory_likecount;

        @BindView(R.id.vp_merchantdetailstory_media)
        ViewPager vp_merchantdetailstory_media;

        @BindView(R.id.cpi_merchantdetailstory)
        CirclePageIndicator cpi_merchantdetailstory;

        @BindView(R.id.ll_merchantdetailstory_position)
        LinearLayout ll_merchantdetailstory_position;

        @BindView(R.id.tv_merchantdetailstory_position)
        TextView tv_merchantdetailstory_position;

        @BindView(R.id.tv_merchantdetailstory_total)
        TextView tv_merchantdetailstory_total;

        @BindView(R.id.fl_merchantdetailstory_media)
        FrameLayout fl_merchantdetailstory_media;

        @BindView(R.id.ll_merchantdetailstory_comment)
        LinearLayout ll_merchantdetailstory_comment;

        @BindView(R.id.cb_merchantdetailstory_like)
        CheckBox cb_merchantdetailstory_like;

        @BindString(R.string.cloud_front_user_avatar)
        String cloud_front_user_avatar;

        @BindColor(R.color.pointColor)
        int pointColor;

        public MerchantDetailStoryViewHolder(MerchantDetailStoryPresenter merchantDetailStoryPresenter, List<Story> stories, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.merchantDetailStoryPresenter = merchantDetailStoryPresenter;
            this.stories = stories;

            this.hashTagHelper = HashTagHelper.Creator.create(pointColor, this);
            this.hashTagHelper.handle(tv_merchantdetailstory_content);

        }

        @Optional
        @OnClick({R.id.ib_merchantdetailstory_comment, R.id.tv_merchantdetailmain_storymorecomment, R.id.ll_merchantdetailstory_comment})
        public void onClickComment() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            merchantDetailStoryPresenter.onClickComment(story, position);
        }

        @OnPageChange(R.id.vp_merchantdetailstory_media)
        public void onPageSelected(int position) {
            String positionMessage = (position + 1) + "/";
            tv_merchantdetailstory_position.setText(positionMessage);
        }

        @OnCheckedChanged(R.id.cb_merchantdetailstory_like)
        public void onLikeChecked(boolean checked) {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            Boolean isFirstLikeChecked = story.getFirstLikeChecked();

            if (isFirstLikeChecked) {
                merchantDetailStoryPresenter.onLikeChecked(position, story, checked);
            }
        }

        @OnClick(R.id.ib_merchantdetailstory_menu)
        public void onClickMenu() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            merchantDetailStoryPresenter.onClickMenu(story, position);
        }


        @Override
        public void onHashTagClicked(String tagName) {
            merchantDetailStoryPresenter.onHashTagClicked(tagName);
        }

    }
}
