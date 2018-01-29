package com.stm.story.detail.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
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
import com.stm.story.detail.presenter.StoryDetailPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by ㅇㅇ on 2017-08-28.
 */

public class StoryDetailFileAdapter extends PagerAdapter {
    private StoryDetailPresenter storyDetailPresenter;
    private List<File> files;
    private Context context;
    private int layout;
    private StoryDetailFileViewHolder storyDetailFileViewHolder;

    public StoryDetailFileAdapter(StoryDetailPresenter storyDetailPresenter, List<File> files, Context context, int layout) {
        this.storyDetailPresenter = storyDetailPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layout, container, false);

        File file = files.get(position);
        int type = file.getType();

        storyDetailFileViewHolder = new StoryDetailFileViewHolder(storyDetailPresenter, file, view);
        view.setTag(storyDetailFileViewHolder);

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            String uri = storyDetailFileViewHolder.storyImageUrl + file.getName() + "." + file.getExt();

            storyDetailFileViewHolder.ll_storydetail_photo.setVisibility(View.VISIBLE);
            storyDetailFileViewHolder.ll_storydetail_video.setVisibility(View.GONE);
            storyDetailFileViewHolder.ll_storydetail_vr.setVisibility(View.GONE);

            storyDetailFileViewHolder.ll_storydetail_photo.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(storyDetailFileViewHolder.iv_storydetail_photo);
        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            String uri = storyDetailFileViewHolder.storyVideoUrl + file.getName() + "." + file.getExt();

            storyDetailFileViewHolder.ll_storydetail_photo.setVisibility(View.GONE);
            storyDetailFileViewHolder.ll_storydetail_video.setVisibility(View.VISIBLE);
            storyDetailFileViewHolder.ll_storydetail_vr.setVisibility(View.GONE);

            storyDetailFileViewHolder.ll_storydetail_video.bringToFront();
            storyDetailFileViewHolder.iv_storydetail_videoplayer.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(storyDetailFileViewHolder.iv_storydetail_video);
        }

        if (type == DefaultFileFlag.VR360_TYPE) {
            String uri = storyDetailFileViewHolder.storyVr360Url + file.getName() + "." + file.getExt();
            storyDetailFileViewHolder.ll_storydetail_photo.setVisibility(View.GONE);
            storyDetailFileViewHolder.ll_storydetail_video.setVisibility(View.GONE);
            storyDetailFileViewHolder.ll_storydetail_vr.setVisibility(View.VISIBLE);

            storyDetailFileViewHolder.ll_storydetail_vr.bringToFront();
            Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType = VrPanoramaView.Options.TYPE_MONO;
                    storyDetailFileViewHolder.vrpv_storydetail_vr.setInfoButtonEnabled(false);
                    storyDetailFileViewHolder.vrpv_storydetail_vr.loadImageFromBitmap(resource, options);
                }
            });
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public static class StoryDetailFileViewHolder {
        private StoryDetailPresenter storyDetailPresenter;
        private File file;

        @BindView(R.id.ll_storydetail_photo)
        LinearLayout ll_storydetail_photo;

        @BindView(R.id.ll_storydetail_video)
        LinearLayout ll_storydetail_video;

        @BindView(R.id.ll_storydetail_vr)
        LinearLayout ll_storydetail_vr;

        @BindView(R.id.iv_storydetail_photo)
        ImageView iv_storydetail_photo;

        @BindView(R.id.iv_storydetail_videoplayer)
        ImageView iv_storydetail_videoplayer;

        @BindView(R.id.iv_storydetail_video)
        ImageView iv_storydetail_video;

        @BindView(R.id.vrpv_storydetail_vr)
        VrPanoramaView vrpv_storydetail_vr;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        @BindString(R.string.cloud_front_story_video)
        String storyVideoUrl;

        @BindString(R.string.cloud_front_story_vr360)
        String storyVr360Url;

        public StoryDetailFileViewHolder(StoryDetailPresenter storyDetailPresenter, File file, View view) {
            this.storyDetailPresenter = storyDetailPresenter;
            this.file = file;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.iv_storydetail_videoplayer)
        public void onClickPlayerButton() {
            storyDetailPresenter.onClickPlayerButton(file);
        }

        @OnClick(R.id.iv_storydetail_photo)
        public void onClickPhoto() {
            storyDetailPresenter.onClickPhoto(file);
        }

        @OnTouch(R.id.vrpv_storydetail_vr)
        public boolean onTouchVrView() {
            return true;
        }

    }
}
