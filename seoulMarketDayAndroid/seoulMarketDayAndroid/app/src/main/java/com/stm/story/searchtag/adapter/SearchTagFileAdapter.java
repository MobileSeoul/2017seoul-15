package com.stm.story.searchtag.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
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
import com.stm.story.searchtag.presenter.SearchTagPresenter;
import com.stm.user.detail.merchant.fragment.story.adapter.MerchantDetailStoryFileAdapter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public class SearchTagFileAdapter extends PagerAdapter {
    private SearchTagPresenter searchTagPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    public SearchTagFileAdapter(SearchTagPresenter searchTagPresenter, List<File> files, Context context, int layout) {
        this.searchTagPresenter = searchTagPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layout, null);
        File file = files.get(position);
        int type = file.getType();

        final SearchTagFileViewHolder merchantDetailStoryFileViewHolder = new SearchTagFileViewHolder(searchTagPresenter, file, view);
        view.setTag(merchantDetailStoryFileViewHolder);

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            String uri = merchantDetailStoryFileViewHolder.storyImageUrl + file.getName() + "." + file.getExt();

            merchantDetailStoryFileViewHolder.ll_searchtag_photo.setVisibility(View.VISIBLE);
            merchantDetailStoryFileViewHolder.ll_searchtag_video.setVisibility(View.GONE);
            merchantDetailStoryFileViewHolder.ll_searchtag_vr.setVisibility(View.GONE);

            merchantDetailStoryFileViewHolder.ll_searchtag_photo.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(merchantDetailStoryFileViewHolder.iv_searchtag_photo);
        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            String uri = merchantDetailStoryFileViewHolder.storyVideoUrl + file.getName() + "." + file.getExt();

            merchantDetailStoryFileViewHolder.ll_searchtag_photo.setVisibility(View.GONE);
            merchantDetailStoryFileViewHolder.ll_searchtag_video.setVisibility(View.VISIBLE);
            merchantDetailStoryFileViewHolder.ll_searchtag_vr.setVisibility(View.GONE);

            merchantDetailStoryFileViewHolder.ll_searchtag_video.bringToFront();
            merchantDetailStoryFileViewHolder.iv_searchtag_videoplayer.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(merchantDetailStoryFileViewHolder.iv_searchtag_video);
        }

        if (type == DefaultFileFlag.VR360_TYPE) {
            String uri = merchantDetailStoryFileViewHolder.storyVr360Url + file.getName() + "." + file.getExt();
            merchantDetailStoryFileViewHolder.ll_searchtag_photo.setVisibility(View.GONE);
            merchantDetailStoryFileViewHolder.ll_searchtag_video.setVisibility(View.GONE);
            merchantDetailStoryFileViewHolder.ll_searchtag_vr.setVisibility(View.VISIBLE);

            merchantDetailStoryFileViewHolder.ll_searchtag_vr.bringToFront();
            Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType = VrPanoramaView.Options.TYPE_MONO;
                    merchantDetailStoryFileViewHolder.vrpv_searchtag_vr.setInfoButtonEnabled(false);
                    merchantDetailStoryFileViewHolder.vrpv_searchtag_vr.loadImageFromBitmap(resource, options);
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


    public static class SearchTagFileViewHolder {
        private SearchTagPresenter searchTagPresenter;
        private File file;

        @BindView(R.id.ll_searchtag_photo)
        LinearLayout ll_searchtag_photo;

        @BindView(R.id.ll_searchtag_video)
        LinearLayout ll_searchtag_video;

        @BindView(R.id.ll_searchtag_vr)
        LinearLayout ll_searchtag_vr;

        @BindView(R.id.iv_searchtag_photo)
        ImageView iv_searchtag_photo;

        @BindView(R.id.iv_searchtag_videoplayer)
        ImageView iv_searchtag_videoplayer;

        @BindView(R.id.iv_searchtag_video)
        ImageView iv_searchtag_video;

        @BindView(R.id.vrpv_searchtag_vr)
        VrPanoramaView vrpv_searchtag_vr;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        @BindString(R.string.cloud_front_story_video)
        String storyVideoUrl;

        @BindString(R.string.cloud_front_story_vr360)
        String storyVr360Url;

        public SearchTagFileViewHolder(SearchTagPresenter searchTagPresenter, File file, View itemView) {
            this.searchTagPresenter = searchTagPresenter;
            this.file = file;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_searchtag_videoplayer)
        public void onClickPlayerButton() {
            searchTagPresenter.onClickPlayerButton(file);
        }

        @OnClick(R.id.iv_searchtag_photo)
        public void onClickPhoto() {
            searchTagPresenter.onClickPhoto(file);
        }

        @OnTouch(R.id.vrpv_searchtag_vr)
        public boolean onTouchVrView() {
            return true;
        }

    }
}
