package com.stm.story.edit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.stm.common.dto.FileDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.BitmapUtil;
import com.stm.story.edit.presenter.StoryEditPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public class ContentUriFileAdapter extends RecyclerView.Adapter<ContentUriFileAdapter.StoryEditContentUriViewHolder> {
    private StoryEditPresenter storyEditPresenter;
    private ArrayList<FileDto> fileDtos;
    private Context context;
    private int layout;
    private boolean isVideoFull;

    public ContentUriFileAdapter(StoryEditPresenter storyEditPresenter, ArrayList<FileDto> fileDtos, Context context, int layout) {
        this.storyEditPresenter = storyEditPresenter;
        this.fileDtos = fileDtos;
        this.context = context;
        this.layout = layout;
        this.isVideoFull = false;
    }

    @Override
    public StoryEditContentUriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StoryEditContentUriViewHolder storyEditContentUriViewHolder = new StoryEditContentUriViewHolder(storyEditPresenter, fileDtos, LayoutInflater.from(context).inflate(layout, parent, false));
        return storyEditContentUriViewHolder;
    }

    @Override
    public void onBindViewHolder(final StoryEditContentUriViewHolder holder, int position) {
        FileDto fileDto = fileDtos.get(position);
        int type = fileDto.getType();
        Uri uri = fileDto.getUri();

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            holder.ll_storyedit_photo.setVisibility(View.VISIBLE);
            holder.ll_storyedit_photo.bringToFront();
            holder.iv_storyedit_remove.bringToFront();

            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_storyedit_photo);
        }

        if (type == DefaultFileFlag.VIDEO_TYPE) {
            holder.ll_storyedit_video.setVisibility(View.VISIBLE);
            holder.ll_storyedit_video.bringToFront();
            holder.iv_storyedit_remove.bringToFront();
            holder.iv_storyedit_videoplayer.bringToFront();

            BitmapUtil bitmapUtil = new BitmapUtil(context);
            Bitmap bitmap = bitmapUtil.getVideoThumbnailBitmapByContentUri(uri);
            holder.iv_storyedit_video.setImageBitmap(bitmap);
            this.isVideoFull = true;
        }

        if (type == DefaultFileFlag.VR360_TYPE) {
            holder.ll_storyedit_vr.setVisibility(View.VISIBLE);
            holder.ll_storyedit_vr.bringToFront();
            holder.iv_storyedit_remove.bringToFront();

            Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
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
        return fileDtos.size();
    }

    public boolean isVideoFull() {
        return isVideoFull;
    }

    public void setVideoFull(boolean videoFull) {
        isVideoFull = videoFull;
    }

    public static class StoryEditContentUriViewHolder extends RecyclerView.ViewHolder {
        private StoryEditPresenter storyEditPresenter;
        private ArrayList<FileDto> fileDtos;

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

        public StoryEditContentUriViewHolder(StoryEditPresenter storyEditPresenter, ArrayList<FileDto> fileDtos, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.storyEditPresenter = storyEditPresenter;
            this.fileDtos = fileDtos;
        }

        @OnClick(R.id.iv_storyedit_remove)
        public void onClickRemoveForContentUriFile() {
            int position = getAdapterPosition();
            FileDto fileDto = fileDtos.get(position);
            storyEditPresenter.onClickRemoveForContentUriFile(fileDto, position);
        }
    }
}
