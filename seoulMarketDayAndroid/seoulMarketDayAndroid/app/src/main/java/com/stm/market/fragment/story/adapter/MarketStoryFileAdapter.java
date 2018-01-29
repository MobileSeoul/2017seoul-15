package com.stm.market.fragment.story.adapter;

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
import com.stm.market.fragment.story.presenter.MarketStoryPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketStoryFileAdapter extends PagerAdapter {
    private MarketStoryPresenter marketStoryPresenter;
    private MarketStoryFileViewHolder marketStoryFileViewHolder;
    private List<File> files;
    private Context context;
    private int layout;

    public MarketStoryFileAdapter(MarketStoryPresenter marketStoryPresenter, List<File> files, Context context, int layout) {
        this.marketStoryPresenter = marketStoryPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layout, container, false);

        File file = files.get(position);
        int type = file.getType();

        marketStoryFileViewHolder = new MarketStoryFileViewHolder(marketStoryPresenter, file, view);

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            String uri = marketStoryFileViewHolder.storyImageUrl + file.getName() + "." + file.getExt();

            marketStoryFileViewHolder.ll_marketstory_photo.setVisibility(View.VISIBLE);
            marketStoryFileViewHolder.ll_marketstory_video.setVisibility(View.GONE);
            marketStoryFileViewHolder.ll_marketstory_vr.setVisibility(View.GONE);

            marketStoryFileViewHolder.ll_marketstory_photo.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(marketStoryFileViewHolder.iv_marketstory_photo);
        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            String uri = marketStoryFileViewHolder.storyVideoUrl + file.getName() + "." + file.getExt();

            marketStoryFileViewHolder.ll_marketstory_photo.setVisibility(View.GONE);
            marketStoryFileViewHolder.ll_marketstory_video.setVisibility(View.VISIBLE);
            marketStoryFileViewHolder.ll_marketstory_vr.setVisibility(View.GONE);

            marketStoryFileViewHolder.ll_marketstory_video.bringToFront();
            marketStoryFileViewHolder.iv_marketstory_videoplayer.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(marketStoryFileViewHolder.iv_marketstory_video);
        }

        if (type == DefaultFileFlag.VR360_TYPE) {
            String uri = marketStoryFileViewHolder.storyVr360Url + file.getName() + "." + file.getExt();
            marketStoryFileViewHolder.ll_marketstory_photo.setVisibility(View.GONE);
            marketStoryFileViewHolder.ll_marketstory_video.setVisibility(View.GONE);
            marketStoryFileViewHolder.ll_marketstory_vr.setVisibility(View.VISIBLE);

            marketStoryFileViewHolder.ll_marketstory_vr.bringToFront();
            Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType = VrPanoramaView.Options.TYPE_MONO;
                    marketStoryFileViewHolder.vrpv_marketstory_vr.setInfoButtonEnabled(false);
                    marketStoryFileViewHolder.vrpv_marketstory_vr.loadImageFromBitmap(resource, options);
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
        container.removeView((View)object);
    }

    public static class MarketStoryFileViewHolder {
        private MarketStoryPresenter marketStoryPresenter;
        private File file;

        @BindView(R.id.ll_marketstory_photo)
        LinearLayout ll_marketstory_photo;

        @BindView(R.id.ll_marketstory_video)
        LinearLayout ll_marketstory_video;

        @BindView(R.id.ll_marketstory_vr)
        LinearLayout ll_marketstory_vr;

        @BindView(R.id.iv_marketstory_photo)
        ImageView iv_marketstory_photo;

        @BindView(R.id.iv_marketstory_video)
        ImageView iv_marketstory_video;

        @BindView(R.id.iv_marketstory_videoplayer)
        ImageView iv_marketstory_videoplayer;

        @BindView(R.id.vrpv_marketstory_vr)
        VrPanoramaView vrpv_marketstory_vr;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        @BindString(R.string.cloud_front_story_video)
        String storyVideoUrl;

        @BindString(R.string.cloud_front_story_vr360)
        String storyVr360Url;

        public MarketStoryFileViewHolder(MarketStoryPresenter marketStoryPresenter, File file, View view) {
            ButterKnife.bind(this, view);
            this.marketStoryPresenter = marketStoryPresenter;
            this.file = file;
        }

        @OnClick(R.id.iv_marketstory_videoplayer)
        public void onClickPlayer() {
            marketStoryPresenter.onClickPlayer(file);
        }

        @OnClick(R.id.iv_marketstory_photo)
        public void onClickPhoto() {
            marketStoryPresenter.onClickPhoto(file);
        }

        @OnTouch(R.id.vrpv_marketstory_vr)
        public boolean onTouchVrView() {
            return true;
        }
    }
}
