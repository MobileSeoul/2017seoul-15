package com.stm.comment.detail.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.CalculateDateUtil;
import com.stm.comment.detail.presenter.CommentReplyPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Dev-0 on 2017-08-10.
 */

public class CommentReplyAdapter extends RecyclerView.Adapter<CommentReplyAdapter.CommentReplyViewHolder> {
    private CommentReplyPresenter commentReplyPresenter;
    private List<StoryComment> storyComments;
    private Context context;

    public CommentReplyAdapter(CommentReplyPresenter commentReplyPresenter, List<StoryComment> storyComments, Context context) {
        this.commentReplyPresenter = commentReplyPresenter;
        this.storyComments = storyComments;
        this.context = context;
    }

    @Override
    public CommentReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentReplyViewHolder commentReplyViewHolder = new CommentReplyViewHolder(commentReplyPresenter, storyComments, LayoutInflater.from(context).inflate(R.layout.item_commentreply, parent, false));
        return commentReplyViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommentReplyViewHolder holder, int position) {
        StoryComment storyComment = storyComments.get(position);
        String content = storyComment.getContent();
        String date = storyComment.getCreatedAt();
        File file = storyComment.getFile();

        User user = storyComment.getUser();
        String name = user.getName();
        String avatar = user.getAvatar();


        holder.tv_commentreply_name.setText(name);

        if (date != null) {
            holder.tv_commentreply_date.setText(CalculateDateUtil.getCalculateDateByDateTime(date));
        } else {
            holder.tv_commentreply_date.setText("방금");
        }
        Glide.with(context).load(holder.userAvatarUrl + avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_commentreply_avatar);

        if (file != null) {
            holder.fl_commentreply_files.setVisibility(View.VISIBLE);
            holder.tv_commentreply_content.setVisibility(View.GONE);
            int type = file.getType();

            if (type == DefaultFileFlag.PHOTO_TYPE) {
                String uri = holder.commentImageUrl + file.getName() + "." + file.getExt();

                holder.ll_commentreply_photo.setVisibility(View.VISIBLE);
                holder.ll_commentreply_video.setVisibility(View.GONE);
                holder.ll_commentreply_vr.setVisibility(View.GONE);

                holder.ll_commentreply_photo.bringToFront();
                Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_commentreply_photo);
            }

            if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
                String uri = holder.commentVideoUrl + file.getName() + "." + file.getExt();

                holder.ll_commentreply_video.setVisibility(View.VISIBLE);
                holder.ll_commentreply_photo.setVisibility(View.GONE);
                holder.ll_commentreply_vr.setVisibility(View.GONE);

                holder.ll_commentreply_video.bringToFront();
                holder.iv_commentreply_videoplayer.bringToFront();
                Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_commentreply_video);

            }

            if (type == DefaultFileFlag.VR360_TYPE) {
                String uri = holder.commentVr360Url + file.getName() + "." + file.getExt();
                holder.ll_commentreply_vr.setVisibility(View.VISIBLE);
                holder.ll_commentreply_photo.setVisibility(View.GONE);
                holder.ll_commentreply_video.setVisibility(View.GONE);

                holder.ll_commentreply_vr.bringToFront();
                Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        VrPanoramaView.Options options = new VrPanoramaView.Options();
                        options.inputType = VrPanoramaView.Options.TYPE_MONO;
                        holder.vrpv_commentreply_vr.setInfoButtonEnabled(false);
                        holder.vrpv_commentreply_vr.loadImageFromBitmap(resource, options);
                    }
                });
            }
        } else {
            holder.tv_commentreply_content.setVisibility(View.VISIBLE);
            holder.fl_commentreply_files.setVisibility(View.GONE);
            holder.tv_commentreply_content.setText(content);
        }
    }

    @Override
    public int getItemCount() {
        return storyComments.size();
    }

    public static class CommentReplyViewHolder extends RecyclerView.ViewHolder {
        private CommentReplyPresenter commentReplyPresenter;
        private List<StoryComment> storyComments;

        @BindView(R.id.ll_commentreply)
        LinearLayout ll_commentreply;

        @BindView(R.id.iv_commentreply_avatar)
        ImageView iv_commentreply_avatar;

        @BindView(R.id.tv_commentreply_name)
        TextView tv_commentreply_name;

        @BindView(R.id.tv_commentreply_content)
        TextView tv_commentreply_content;

        @BindView(R.id.tv_commentreply_date)
        TextView tv_commentreply_date;

        @BindView(R.id.fl_commentreply_files)
        FrameLayout fl_commentreply_files;

        @BindView(R.id.ll_commentreply_video)
        LinearLayout ll_commentreply_video;

        @BindView(R.id.iv_commentreply_video)
        ImageView iv_commentreply_video;

        @BindView(R.id.iv_commentreply_videoplayer)
        ImageView iv_commentreply_videoplayer;

        @BindView(R.id.ll_commentreply_photo)
        LinearLayout ll_commentreply_photo;

        @BindView(R.id.iv_commentreply_photo)
        ImageView iv_commentreply_photo;

        @BindView(R.id.ll_commentreply_vr)
        LinearLayout ll_commentreply_vr;

        @BindView(R.id.vrpv_commentreply_vr)
        VrPanoramaView vrpv_commentreply_vr;


        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        @BindString(R.string.cloud_front_comment_image)
        String commentImageUrl;

        @BindString(R.string.cloud_front_comment_vr360)
        String commentVr360Url;

        @BindString(R.string.cloud_front_comment_video)
        String commentVideoUrl;

        public CommentReplyViewHolder(CommentReplyPresenter commentReplyPresenter, List<StoryComment> storyComments, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.commentReplyPresenter = commentReplyPresenter;
            this.storyComments = storyComments;
        }

        @OnLongClick(R.id.ll_commentreply)
        public boolean onLongClickCommentReplyLayout() {
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            return this.commentReplyPresenter.onLongClickCommentReplyLayout(storyComment, position);
        }

        @OnClick({R.id.iv_commentreply_videoplayer, R.id.iv_commentreply_video})
        public void onClickPlayerButton() {
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            File file = storyComment.getFile();
            commentReplyPresenter.onClickPlayerButton(file);
        }

        @OnClick({R.id.ll_commentreply_photo, R.id.iv_commentreply_photo})
        public void onClickPhotoLayout() {
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            File file = storyComment.getFile();
            commentReplyPresenter.onClickPhotoLayout(file);
        }

        @OnClick({R.id.iv_commentreply_avatar, R.id.tv_commentreply_name})
        public void onClickAvatar(){
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            User user = storyComment.getUser();
            commentReplyPresenter.onClickAvatar(user);
        }


    }
}
