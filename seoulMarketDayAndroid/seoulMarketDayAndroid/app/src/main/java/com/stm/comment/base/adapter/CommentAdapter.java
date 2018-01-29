package com.stm.comment.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.stm.comment.base.presenter.CommentPresenter;
import com.stm.common.dao.File;
import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.CalculateDateUtil;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by ㅇㅇ on 2017-07-27.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private CommentPresenter commentPresenter;
    private List<StoryComment> storyComments;
    private Context context;

    public CommentAdapter(CommentPresenter commentPresenter, List<StoryComment> storyComments, Context context) {
        this.commentPresenter = commentPresenter;
        this.storyComments = storyComments;
        this.context = context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentViewHolder commentViewHolder = new CommentViewHolder(commentPresenter, storyComments, LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false));
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommentViewHolder holder, int position) {
        StoryComment storyComment = storyComments.get(position);
        long depth = storyComment.getDepth();
        int replyCommentCount = storyComment.getReplyCommentCount();
        String content = storyComment.getContent();
        String date = storyComment.getCreatedAt();

        File file = storyComment.getFile();

        User user = storyComment.getUser();
        String name = user.getName();
        String avatar = user.getAvatar();

        if (depth == 0) {
            holder.ll_comment.setVisibility(View.VISIBLE);
            holder.ll_comment_reply.setVisibility(View.GONE);

            holder.tv_comment_name.setText(name);

            if (date != null) {
                holder.tv_comment_date.setText(CalculateDateUtil.getCalculateDateByDateTime(date));
            } else {
                holder.tv_comment_date.setText("방금");
            }
            Glide.with(context).load(holder.userAvatarUrl + avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_comment_avatar);

            if (file != null) {
                holder.fl_comment_files.setVisibility(View.VISIBLE);
                holder.tv_comment_content.setVisibility(View.GONE);
                int type = file.getType();

                if (type == DefaultFileFlag.PHOTO_TYPE) {
                    String uri = holder.commentImageUrl + file.getName() + "." + file.getExt();

                    holder.ll_comment_photo.setVisibility(View.VISIBLE);
                    holder.ll_comment_video.setVisibility(View.GONE);
                    holder.ll_comment_vr.setVisibility(View.GONE);

                    holder.ll_comment_photo.bringToFront();
                    Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_comment_photo);
                }

                if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
                    String uri = holder.commentVideoUrl + file.getName() + "." + file.getExt();

                    holder.ll_comment_video.setVisibility(View.VISIBLE);
                    holder.ll_comment_photo.setVisibility(View.GONE);
                    holder.ll_comment_vr.setVisibility(View.GONE);

                    holder.ll_comment_video.bringToFront();
                    holder.iv_comment_videoplayer.bringToFront();
                    Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_comment_video);

                }

                if (type == DefaultFileFlag.VR360_TYPE) {
                    String uri = holder.commentVr360Url + file.getName() + "." + file.getExt();
                    holder.ll_comment_vr.setVisibility(View.VISIBLE);
                    holder.ll_comment_photo.setVisibility(View.GONE);
                    holder.ll_comment_video.setVisibility(View.GONE);

                    holder.ll_comment_vr.bringToFront();
                    Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            VrPanoramaView.Options options = new VrPanoramaView.Options();
                            options.inputType = VrPanoramaView.Options.TYPE_MONO;
                            holder.vrpv_comment_vr.setInfoButtonEnabled(false);
                            holder.vrpv_comment_vr.loadImageFromBitmap(resource, options);
                        }
                    });
                }
            } else {
                holder.tv_comment_content.setVisibility(View.VISIBLE);
                holder.fl_comment_files.setVisibility(View.GONE);
                holder.tv_comment_content.setText(content);
            }

        }

        if (depth == 1) {
            holder.ll_comment_reply.setVisibility(View.VISIBLE);
            holder.ll_comment.setVisibility(View.GONE);

            holder.tv_comment_replyname.setText(name);

            if (date != null) {
                holder.tv_comment_replydate.setText(CalculateDateUtil.getCalculateDateByDateTime(date));
            } else {
                holder.tv_comment_replydate.setText("방금");
            }
            Glide.with(context).load(holder.userAvatarUrl + avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_comment_replyavatar);

            if (replyCommentCount > 1) {
                holder.ll_comment_replycount.setVisibility(View.VISIBLE);
                holder.tv_comment_replycount.setText(replyCommentCount + "");
            }

            if (file != null) {
                holder.fl_comment_replyfiles.setVisibility(View.VISIBLE);
                holder.tv_comment_replycontent.setVisibility(View.GONE);
                int type = file.getType();

                if (type == DefaultFileFlag.PHOTO_TYPE) {
                    String uri = holder.commentImageUrl + file.getName() + "." + file.getExt();

                    holder.ll_comment_replyphoto.setVisibility(View.VISIBLE);
                    holder.ll_comment_replyvideo.setVisibility(View.GONE);
                    holder.ll_comment_replyvr.setVisibility(View.GONE);

                    holder.ll_comment_replyphoto.bringToFront();
                    Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_comment_replyphoto);
                }

                if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
                    String uri = holder.commentVideoUrl + file.getName() + "." + file.getExt();

                    holder.ll_comment_replyvideo.setVisibility(View.VISIBLE);
                    holder.ll_comment_replyphoto.setVisibility(View.GONE);
                    holder.ll_comment_replyvr.setVisibility(View.GONE);

                    holder.ll_comment_replyvideo.bringToFront();
                    holder.iv_comment_replyvideoplayer.bringToFront();
                    Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_comment_replyvideo);

                }

                if (type == DefaultFileFlag.VR360_TYPE) {
                    String uri = holder.commentVr360Url + file.getName() + "." + file.getExt();
                    holder.ll_comment_replyvr.setVisibility(View.VISIBLE);
                    holder.ll_comment_replyphoto.setVisibility(View.GONE);
                    holder.ll_comment_replyvideo.setVisibility(View.GONE);

                    holder.ll_comment_replyvr.bringToFront();
                    Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            VrPanoramaView.Options options = new VrPanoramaView.Options();
                            options.inputType = VrPanoramaView.Options.TYPE_MONO;
                            holder.vrpv_comment_replyvr.setInfoButtonEnabled(false);
                            holder.vrpv_comment_replyvr.loadImageFromBitmap(resource, options);
                        }
                    });
                }
            } else {
                holder.tv_comment_replycontent.setVisibility(View.VISIBLE);
                holder.fl_comment_replyfiles.setVisibility(View.GONE);
                holder.tv_comment_replycontent.setText(content);
            }
        }
    }

    @Override
    public int getItemCount() {
        return storyComments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private CommentPresenter commentPresenter;
        private List<StoryComment> storyComments;

        @BindView(R.id.ll_comment)
        LinearLayout ll_comment;

        @BindView(R.id.iv_comment_avatar)
        ImageView iv_comment_avatar;

        @BindView(R.id.tv_comment_name)
        TextView tv_comment_name;

        @BindView(R.id.tv_comment_content)
        TextView tv_comment_content;

        @BindView(R.id.tv_comment_date)
        TextView tv_comment_date;

        @BindView(R.id.fl_comment_files)
        FrameLayout fl_comment_files;

        @BindView(R.id.ll_comment_video)
        LinearLayout ll_comment_video;

        @BindView(R.id.iv_comment_video)
        ImageView iv_comment_video;

        @BindView(R.id.iv_comment_videoplayer)
        ImageView iv_comment_videoplayer;

        @BindView(R.id.ll_comment_photo)
        LinearLayout ll_comment_photo;

        @BindView(R.id.iv_comment_photo)
        ImageView iv_comment_photo;

        @BindView(R.id.ll_comment_vr)
        LinearLayout ll_comment_vr;

        @BindView(R.id.vrpv_comment_vr)
        VrPanoramaView vrpv_comment_vr;

        @BindView(R.id.tv_comment_replycontent)
        TextView tv_comment_replycontent;

        @BindView(R.id.iv_comment_replyavatar)
        ImageView iv_comment_replyavatar;

        @BindView(R.id.tv_comment_replyname)
        TextView tv_comment_replyname;

        @BindView(R.id.tv_comment_replydate)
        TextView tv_comment_replydate;

        @BindView(R.id.tv_comment_replycount)
        TextView tv_comment_replycount;

        @BindView(R.id.ll_comment_reply)
        LinearLayout ll_comment_reply;

        @BindView(R.id.ll_comment_replycount)
        LinearLayout ll_comment_replycount;

        @BindView(R.id.fl_comment_replyfiles)
        FrameLayout fl_comment_replyfiles;

        @BindView(R.id.ll_comment_replyvideo)
        LinearLayout ll_comment_replyvideo;

        @BindView(R.id.iv_comment_replyvideoplayer)
        ImageView iv_comment_replyvideoplayer;

        @BindView(R.id.iv_comment_replyvideo)
        ImageView iv_comment_replyvideo;

        @BindView(R.id.ll_comment_replyphoto)
        LinearLayout ll_comment_replyphoto;

        @BindView(R.id.iv_comment_replyphoto)
        ImageView iv_comment_replyphoto;

        @BindView(R.id.ll_comment_replyvr)
        LinearLayout ll_comment_replyvr;

        @BindView(R.id.vrpv_comment_replyvr)
        VrPanoramaView vrpv_comment_replyvr;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        @BindString(R.string.cloud_front_comment_image)
        String commentImageUrl;

        @BindString(R.string.cloud_front_comment_vr360)
        String commentVr360Url;

        @BindString(R.string.cloud_front_comment_video)
        String commentVideoUrl;

        public CommentViewHolder(CommentPresenter commentPresenter, List<StoryComment> storyComments, View itemView) {
            super(itemView);
            this.commentPresenter = commentPresenter;
            this.storyComments = storyComments;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_comment_reply)
        public void onClickReply() {
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            this.commentPresenter.onClickReply(storyComment, position);
        }

        @OnClick(R.id.ll_comment_reply)
        public void onClickReplyLayout() {
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            this.commentPresenter.onClickReply(storyComment, position - 1);

        }

        @OnLongClick(R.id.ll_comment)
        public boolean onLongClickCommentLayout() {
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            return this.commentPresenter.onLongClickCommentLayout(storyComment, position);
        }

        @OnClick({R.id.iv_comment_videoplayer, R.id.iv_comment_replyvideoplayer})
        public void onClickPlayerButton() {
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            File file = storyComment.getFile();
            commentPresenter.onClickPlayerButton(file);
        }

        @OnClick({R.id.ll_comment_photo, R.id.ll_comment_replyphoto})
        public void onClickPhotoLayout(){
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            File file = storyComment.getFile();
            commentPresenter.onClickPhotoLayout(file);
        }

        @OnClick({R.id.iv_comment_avatar, R.id.tv_comment_name})
        public void onClickAvatar(){
            int position = getAdapterPosition();
            StoryComment storyComment = storyComments.get(position);
            User user = storyComment.getUser();
            commentPresenter.onClickAvatar(user);
        }

    }
}
