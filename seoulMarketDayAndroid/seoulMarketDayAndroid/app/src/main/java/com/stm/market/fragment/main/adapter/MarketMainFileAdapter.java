package com.stm.market.fragment.main.adapter;

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
import com.stm.market.fragment.main.presenter.MarketMainPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketMainFileAdapter extends PagerAdapter {
    private MarketMainPresenter marketMainPresenter;
    private List<File> files;
    private Context context;
    private int layout;
    private MarketMainFileViewHolder marketMainFileViewHolder;

    public MarketMainFileAdapter(MarketMainPresenter marketMainPresenter, List<File> files, Context context, int layout) {
        this.marketMainPresenter = marketMainPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layout, container, false);

        File file = files.get(position);
        int type = file.getType();

        marketMainFileViewHolder = new MarketMainFileViewHolder(marketMainPresenter, file, view);

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            String uri = marketMainFileViewHolder.storyImageUrl + file.getName() + "." + file.getExt();

            marketMainFileViewHolder.ll_marketmain_photo.setVisibility(View.VISIBLE);
            marketMainFileViewHolder.ll_marketmain_video.setVisibility(View.GONE);
            marketMainFileViewHolder.ll_marketmain_vr.setVisibility(View.GONE);

            marketMainFileViewHolder.ll_marketmain_photo.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(marketMainFileViewHolder.iv_marketmain_photo);
        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            String uri = marketMainFileViewHolder.storyVideoUrl + file.getName() + "." + file.getExt();

            marketMainFileViewHolder.ll_marketmain_photo.setVisibility(View.GONE);
            marketMainFileViewHolder.ll_marketmain_video.setVisibility(View.VISIBLE);
            marketMainFileViewHolder.ll_marketmain_vr.setVisibility(View.GONE);

            marketMainFileViewHolder.ll_marketmain_video.bringToFront();
            marketMainFileViewHolder.iv_marketmain_videoplayer.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(marketMainFileViewHolder.iv_marketmain_video);
        }

        if (type == DefaultFileFlag.VR360_TYPE) {
            String uri = marketMainFileViewHolder.storyVr360Url + file.getName() + "." + file.getExt();
            marketMainFileViewHolder.ll_marketmain_photo.setVisibility(View.GONE);
            marketMainFileViewHolder.ll_marketmain_video.setVisibility(View.GONE);
            marketMainFileViewHolder.ll_marketmain_vr.setVisibility(View.VISIBLE);

            marketMainFileViewHolder.ll_marketmain_vr.bringToFront();
            marketMainFileViewHolder.vrpv_marketmain_vr.bringToFront();

            Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType = VrPanoramaView.Options.TYPE_MONO;
                    marketMainFileViewHolder.vrpv_marketmain_vr.setInfoButtonEnabled(false);
                    marketMainFileViewHolder.vrpv_marketmain_vr.loadImageFromBitmap(resource, options);
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

    public static class MarketMainFileViewHolder {
        private MarketMainPresenter marketMainPresenter;
        private File file;

        @BindView(R.id.ll_marketmain_photo)
        LinearLayout ll_marketmain_photo;

        @BindView(R.id.ll_marketmain_video)
        LinearLayout ll_marketmain_video;

        @BindView(R.id.ll_marketmain_vr)
        LinearLayout ll_marketmain_vr;

        @BindView(R.id.iv_marketmain_photo)
        ImageView iv_marketmain_photo;

        @BindView(R.id.iv_marketmain_video)
        ImageView iv_marketmain_video;

        @BindView(R.id.iv_marketmain_videoplayer)
        ImageView iv_marketmain_videoplayer;

        @BindView(R.id.vrpv_marketmain_vr)
        VrPanoramaView vrpv_marketmain_vr;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        @BindString(R.string.cloud_front_story_video)
        String storyVideoUrl;

        @BindString(R.string.cloud_front_story_vr360)
        String storyVr360Url;

        public MarketMainFileViewHolder(MarketMainPresenter marketMainPresenter, File file, View view) {
            ButterKnife.bind(this, view);
            this.marketMainPresenter = marketMainPresenter;
            this.file = file;
        }

        @OnClick(R.id.iv_marketmain_videoplayer)
        public void onClickPlayer() {
            marketMainPresenter.onClickPlayer(file);
        }

        @OnClick(R.id.iv_marketmain_photo)
        public void onClickPhoto() {
            marketMainPresenter.onClickPhoto(file);
        }

        @OnTouch(R.id.vrpv_marketmain_vr)
        public boolean onTouchVrView() {
            return true;
        }

    }
}
