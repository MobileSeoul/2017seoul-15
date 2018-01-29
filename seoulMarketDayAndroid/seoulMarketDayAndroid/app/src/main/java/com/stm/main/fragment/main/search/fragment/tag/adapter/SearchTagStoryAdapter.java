package com.stm.main.fragment.main.search.fragment.tag.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
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
import com.stm.main.fragment.main.search.fragment.tag.presenter.SearchTagPresenter;
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
 * Created by ㅇㅇ on 2017-09-04.
 */

public class SearchTagStoryAdapter extends RecyclerView.Adapter<SearchTagStoryAdapter.SearchTagStoryViewHolder> {
    private SearchTagPresenter searchTagPresenter;
    private List<Story> stories;
    private Context context;
    private int layout;
    private SearchTagFileAdapter searchTagFileAdapter;

    public SearchTagStoryAdapter(SearchTagPresenter searchTagPresenter, List<Story> stories, Context context, int layout) {
        this.searchTagPresenter = searchTagPresenter;
        this.stories = stories;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public SearchTagStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchTagStoryViewHolder searchTagStoryViewHolder = new SearchTagStoryViewHolder(searchTagPresenter, stories, LayoutInflater.from(context).inflate(layout, parent, false));
        return searchTagStoryViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchTagStoryViewHolder holder, int position) {
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

        holder.tv_searchtag_commentcount.setText(commentCount + "");
        holder.tv_searchtag_name.setText(name);
        holder.tv_searchtag_content.setText(content);
        holder.tv_searchtag_date.setText(CalculateDateUtil.getCalculateDateByDateTime(dateTime));
        holder.tv_searchtag_likecount.setText(likeCount + "");
        holder.tv_searchtag_total.setText(String.valueOf(fileSize));
        holder.cb_searchtag_like.setChecked(isLikeChecked);
        story.setFirstLikeChecked(true);
        Glide.with(context).load(holder.cloud_front_user_avatar + avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_searchtag_avatar);

        searchTagPresenter.setSearchTagFileAdapterItem(holder, files);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void setSearchTagFileAdapterItem(SearchTagStoryViewHolder holder, List<File> files) {
        this.searchTagFileAdapter = new SearchTagFileAdapter(searchTagPresenter, files, context, R.layout.item_searchtag_file);
        holder.vp_searchtag_file.setAdapter(searchTagFileAdapter);
    }

    public void gonePosition(SearchTagStoryViewHolder holder) {
        holder.ll_searchtag_position.setVisibility(View.GONE);
    }

    public void goneFile(SearchTagStoryViewHolder holder) {
        holder.vp_searchtag_file.setVisibility(View.GONE);
    }

    public void goneIndicator(SearchTagStoryViewHolder holder) {
        holder.cpi_searchtag.setVisibility(View.GONE);
    }

    public void setSearchTagAdapterLikeUnChecked(SearchTagStoryViewHolder holder) {
        holder.cb_searchtag_like.setChecked(false);
    }


    public static class SearchTagStoryViewHolder extends RecyclerView.ViewHolder implements HashTagHelper.OnHashTagClickListener {
        private SearchTagPresenter searchTagPresenter;
        private List<Story> stories;
        private HashTagHelper hashTagHelper;

        @BindView(R.id.iv_searchtag_avatar)
        ImageView iv_searchtag_avatar;

        @BindView(R.id.tv_searchtag_commentcount)
        TextView tv_searchtag_commentcount;

        @BindView(R.id.tv_searchtag_name)
        TextView tv_searchtag_name;

        @BindView(R.id.tv_searchtag_content)
        TextView tv_searchtag_content;

        @BindView(R.id.tv_searchtag_date)
        TextView tv_searchtag_date;

        @BindView(R.id.tv_searchtag_likecount)
        TextView tv_searchtag_likecount;

        @BindView(R.id.vp_searchtag_file)
        ViewPager vp_searchtag_file;

        @BindView(R.id.cpi_searchtag)
        CirclePageIndicator cpi_searchtag;

        @BindView(R.id.ll_searchtag_position)
        LinearLayout ll_searchtag_position;

        @BindView(R.id.tv_searchtag_position)
        TextView tv_searchtag_position;

        @BindView(R.id.tv_searchtag_total)
        TextView tv_searchtag_total;

        @BindView(R.id.fl_searchtag_file)
        FrameLayout fl_searchtag_file;

        @BindView(R.id.cb_searchtag_like)
        CheckBox cb_searchtag_like;

        @BindString(R.string.cloud_front_user_avatar)
        String cloud_front_user_avatar;

        @BindColor(R.color.pointColor)
        int pointColor;

        public SearchTagStoryViewHolder(SearchTagPresenter searchTagPresenter, List<Story> stories, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.searchTagPresenter = searchTagPresenter;
            this.stories = stories;
            this.hashTagHelper = HashTagHelper.Creator.create(pointColor, this);
            this.hashTagHelper.handle(tv_searchtag_content);
        }

        @OnClick({R.id.tv_searchtag_morecomment, R.id.ib_searchtag_comment})
        public void onClickMoreComment() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            searchTagPresenter.onClickMoreComment(story, position);
        }

        @OnClick({R.id.iv_searchtag_avatar, R.id.tv_searchtag_name})
        public void onClickAvatar() {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            User user = story.getUser();
            searchTagPresenter.onClickAvatar(user);
        }

        @OnCheckedChanged(R.id.cb_searchtag_like)
        public void onLikeChecked(boolean checked) {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            Boolean isFirstLikeChecked = story.getFirstLikeChecked();

            if (isFirstLikeChecked) {
                searchTagPresenter.onLikeChecked(this, story, position, checked);
            }
        }

        @OnPageChange(R.id.vp_searchtag_file)
        public void onPageSelected(int position) {
            String positionMessage = (position + 1) + "/";
            tv_searchtag_position.setText(positionMessage);
        }

        @OnClick(R.id.ib_searchtag_menu)
        public void onClickMenu(){
            int position = getAdapterPosition();
            Story story = stories.get(position);
            searchTagPresenter.onClickMenu(story, position);
        }

        @Override
        public void onHashTagClicked(String hashTag) {

        }
    }
}
