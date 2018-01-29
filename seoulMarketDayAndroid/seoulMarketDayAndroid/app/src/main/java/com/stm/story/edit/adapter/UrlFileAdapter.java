package com.stm.story.edit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.story.edit.presenter.StoryEditPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public class UrlFileAdapter extends RecyclerView.Adapter<UrlFileAdapter.UrlFileViewHolder> {
    private StoryEditPresenter storyEditPresenter;
    private List<File> files;
    private Context context;
    private int layout;
    private boolean isVideoFull;

    public UrlFileAdapter(StoryEditPresenter storyEditPresenter, List<File> files, Context context, int layout) {
        this.storyEditPresenter = storyEditPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
        this.isVideoFull = false;
    }

    @Override
    public UrlFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UrlFileViewHolder urlFileViewHolder = new UrlFileViewHolder(storyEditPresenter, files, LayoutInflater.from(context).inflate(layout, parent, false));
        return urlFileViewHolder;
    }

    @Override
    public void onBindViewHolder(final UrlFileViewHolder holder, int position) {
        File file = files.get(position);
        int type = file.getType();

        String fileName = file.getName();
        String fileExt = file.getExt();
        String url = fileName + "." + fileExt;

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            holder.ll_storyedit_photo.setVisibility(View.VISIBLE);
            holder.ll_storyedit_photo.bringToFront();
            holder.iv_storyedit_remove.bringToFront();

            Glide.with(context).load(holder.storyImageUrl + url).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_storyedit_photo);
        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            holder.ll_storyedit_video.setVisibility(View.VISIBLE);
            holder.ll_storyedit_video.bringToFront();
            holder.iv_storyedit_remove.bringToFront();
            holder.iv_storyedit_videoplayer.bringToFront();
            this.isVideoFull = true;
            Glide.with(context).load(holder.storyVideoUrl + url).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_storyedit_video);
        }

        if (type == DefaultFileFlag.VR360_TYPE) {
            holder.ll_storyedit_vr.setVisibility(View.VISIBLE);
            holder.ll_storyedit_vr.bringToFront();
            holder.iv_storyedit_remove.bringToFront();

            Glide.with(context).load(holder.storyVR360Url + url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType = VrPanoramaView.Options.TYPE_MONO;
                    holder.vrpv_storyedit_vr.setInfoButtonEnabled(false);
                    holder.vrpv_storyedit_vr.loadImageFromBitmap(resource, options);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public boolean isVideoFull() {
        return isVideoFull;
    }

    public void setVideoFull(boolean videoFull) {
        isVideoFull = videoFull;
    }

    public static class UrlFileViewHolder extends RecyclerView.ViewHolder {
        private StoryEditPresenter storyEditPresenter;
        private List<File> files;

        @BindView(R.id.iv_storyedit_remove)
        ImageView iv_storyedit_remove;

        @BindView(R.id.iv_storyedit_photo)
        ImageView iv_storyedit_photo;

        @BindView(R.id.iv_storyedit_videoplayer)
        ImageView iv_storyedit_videoplayer;

        @BindView(R.id.iv_storyedit_video)
        ImageView iv_storyedit_video;

        @BindView(R.id.ll_storyedit_photo)
        LinearLayout ll_storyedit_photo;

        @BindView(R.id.ll_storyedit_video)
        LinearLayout ll_storyedit_video;

        @BindView(R.id.ll_storyedit_vr)
        LinearLayout ll_storyedit_vr;

        @BindView(R.id.vrpv_storyedit_vr)
        VrPanoramaView vrpv_storyedit_vr;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        @BindString(R.string.cloud_front_story_video)
        String storyVideoUrl;

        @BindString(R.string.cloud_front_story_vr360)
        String storyVR360Url;

        public UrlFileViewHolder(StoryEditPresenter storyEditPresenter, List<File> files, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.storyEditPresenter = storyEditPresenter;
            this.files = files;
        }

        @OnClick(R.id.iv_storyedit_remove)
        public void onClickRemoveForUrlFile(){
           int position = getAdapterPosition();
            File file = files.get(position);
            storyEditPresenter.onClickRemoveForUrlFile(file, position);
        }

        @OnClick(R.id.iv_storyedit_videoplayer)
        public void onClickVideoPlayer(){

        }
    }
}
