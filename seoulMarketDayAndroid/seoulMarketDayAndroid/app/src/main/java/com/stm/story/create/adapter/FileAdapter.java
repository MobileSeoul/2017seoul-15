package com.stm.story.create.adapter;

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
import com.stm.common.dto.FileDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.BitmapUtil;
import com.stm.story.create.presenter.StoryCreatePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-07-13.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {
    private StoryCreatePresenter storyCreatePresenter;
    private ArrayList<FileDto> fileDtos;
    private Context context;
    private int layout;
    private boolean isVideoFull;

    public FileAdapter(StoryCreatePresenter storyCreatePresenter, ArrayList<FileDto> fileDtos, Context context, int layout) {
        this.storyCreatePresenter = storyCreatePresenter;
        this.fileDtos = fileDtos;
        this.context = context;
        this.layout = layout;
        this.isVideoFull = false;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FileViewHolder fileViewHolder = new FileViewHolder(storyCreatePresenter, fileDtos, LayoutInflater.from(context).inflate(layout, parent, false));
        return fileViewHolder;
    }

    @Override
    public void onBindViewHolder(final FileViewHolder holder, int position) {
        FileDto fileDto = fileDtos.get(position);
        Uri uri = fileDto.getUri();
        int fileType = fileDto.getType();

        if (fileType == DefaultFileFlag.PHOTO_TYPE) {
            holder.ll_storycreate_photo.setVisibility(View.VISIBLE);
            holder.ll_storycreate_video.setVisibility(View.GONE);
            holder.ll_storycreate_vr.setVisibility(View.GONE);

            holder.ll_storycreate_photo.bringToFront();
            holder.iv_storycreate_remove.bringToFront();

            Glide.with(context).load(uri).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_storycreate_photo);
        }

        if (fileType == DefaultFileFlag.VIDEO_TYPE) {
            BitmapUtil bitmapUtil = new BitmapUtil(context);

            holder.ll_storycreate_video.setVisibility(View.VISIBLE);
            holder.ll_storycreate_photo.setVisibility(View.GONE);
            holder.ll_storycreate_vr.setVisibility(View.GONE);

            this.isVideoFull = true;
            Bitmap bitmap = bitmapUtil.getVideoThumbnailBitmapByContentUri(uri);
            holder.iv_storycreate_video.setImageBitmap(bitmap);

            holder.iv_storycreate_videoplayer.bringToFront();
            holder.iv_storycreate_remove.bringToFront();

        }

        if (fileType == DefaultFileFlag.VR360_TYPE) {
            holder.ll_storycreate_vr.setVisibility(View.VISIBLE);
            holder.ll_storycreate_video.setVisibility(View.GONE);
            holder.ll_storycreate_photo.setVisibility(View.GONE);

            holder.vrpv_storycreate_vr.bringToFront();
            holder.iv_storycreate_remove.bringToFront();

            Glide.with(context).load(uri).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType = VrPanoramaView.Options.TYPE_MONO;
                    holder.vrpv_storycreate_vr.setInfoButtonEnabled(false);
                    holder.vrpv_storycreate_vr.loadImageFromBitmap(resource, options);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return fileDtos.size();
    }

    public void setFileDelete(int position) {
        int fileType = fileDtos.get(position).getType();
        if (fileType == DefaultFileFlag.VIDEO_TYPE) {
            this.isVideoFull = false;
        }
        fileDtos.remove(position);
    }

    public boolean isVideoFull() {
        return isVideoFull;
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        private StoryCreatePresenter storyCreatePresenter;
        private ArrayList<FileDto> fileDtos;

        @BindView(R.id.iv_storycreate_remove)
        ImageView iv_storycreate_remove;

        @BindView(R.id.iv_storycreate_photo)
        ImageView iv_storycreate_photo;

        @BindView(R.id.iv_storycreate_videoplayer)
        ImageView iv_storycreate_videoplayer;

        @BindView(R.id.iv_storycreate_video)
        ImageView iv_storycreate_video;

        @BindView(R.id.ll_storycreate_photo)
        LinearLayout ll_storycreate_photo;

        @BindView(R.id.ll_storycreate_video)
        LinearLayout ll_storycreate_video;

        @BindView(R.id.ll_storycreate_vr)
        LinearLayout ll_storycreate_vr;

        @BindView(R.id.vrpv_storycreate_vr)
        VrPanoramaView vrpv_storycreate_vr;

        public FileViewHolder(StoryCreatePresenter storyCreatePresenter, ArrayList<FileDto> fileDtos, View itemView) {
            super(itemView);
            this.storyCreatePresenter = storyCreatePresenter;
            this.fileDtos = fileDtos;
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.iv_storycreate_remove)
        public void onClickRemoveButton() {
            int position = getAdapterPosition();
            storyCreatePresenter.onClickFileAdapterRemoveButton(position);
        }

        @OnClick(R.id.iv_storycreate_videoplayer)
        public void onClickPlayerButton(){
            int position = getAdapterPosition();
            FileDto fileDto = fileDtos.get(position);
            storyCreatePresenter.onClickPlayerButton(fileDto);
        }
    }
}
