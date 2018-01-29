package com.stm.user.detail.merchant.fragment.story.adapter;

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
import com.stm.user.detail.merchant.fragment.story.presenter.MerchantDetailStoryPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by ㅇㅇ on 2017-07-25.
 */

public class MerchantDetailStoryFileAdapter extends PagerAdapter {
    private MerchantDetailStoryFileViewHolder merchantDetailStoryFileViewHolder;
    private MerchantDetailStoryPresenter merchantDetailStoryPresenter;
    private MerchantDetailStoryFileAdapter merchantDetailMainFileAdapter;
    private List<File> files;
    private Context context;

    public MerchantDetailStoryFileAdapter(MerchantDetailStoryPresenter merchantDetailStoryPresenter, List<File> files, Context context) {
        this.merchantDetailStoryPresenter = merchantDetailStoryPresenter;
        this.files = files;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_merchantdetailstory_file, null);
        File file = files.get(position);
        int type = file.getType();

        merchantDetailStoryFileViewHolder = new MerchantDetailStoryFileViewHolder(merchantDetailStoryPresenter, file, view);
        view.setTag(merchantDetailStoryFileViewHolder);

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            String uri = merchantDetailStoryFileViewHolder.storyImageUrl + file.getName() + "." + file.getExt();

            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_photo.setVisibility(View.VISIBLE);
            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_video.setVisibility(View.GONE);
            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_vr.setVisibility(View.GONE);

            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_photo.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(merchantDetailStoryFileViewHolder.iv_merchantdetailstory_photo);
        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            String uri = merchantDetailStoryFileViewHolder.storyVideoUrl + file.getName() + "." + file.getExt();

            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_photo.setVisibility(View.GONE);
            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_video.setVisibility(View.VISIBLE);
            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_vr.setVisibility(View.GONE);

            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_video.bringToFront();
            merchantDetailStoryFileViewHolder.iv_merchantdetailstory_videoplayer.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(  merchantDetailStoryFileViewHolder.iv_merchantdetailstory_video);
        }

        if (type == DefaultFileFlag.VR360_TYPE) {
            String uri = merchantDetailStoryFileViewHolder.storyVr360Url + file.getName() + "." + file.getExt();
            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_photo.setVisibility(View.GONE);
            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_video.setVisibility(View.GONE);
            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_vr.setVisibility(View.VISIBLE);

            merchantDetailStoryFileViewHolder.ll_merchantdetailstory_vr.bringToFront();
            Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType = VrPanoramaView.Options.TYPE_MONO;
                    merchantDetailStoryFileViewHolder.vrpv_merchantdetailstory_vr.setInfoButtonEnabled(false);
                    merchantDetailStoryFileViewHolder.vrpv_merchantdetailstory_vr.loadImageFromBitmap(resource, options);
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

    public void setMerchantDetailMainFileAdapterItem(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder, List<File> files) {
        this.merchantDetailMainFileAdapter = new MerchantDetailStoryFileAdapter(merchantDetailStoryPresenter, files, context);
        holder.vp_merchantdetailstory_media.setAdapter(merchantDetailMainFileAdapter);
        holder.cpi_merchantdetailstory.setViewPager(holder.vp_merchantdetailstory_media);
        holder.ll_merchantdetailstory_position.bringToFront();
    }

    public static class MerchantDetailStoryFileViewHolder {
        private MerchantDetailStoryPresenter merchantDetailStoryPresenter;
        private File file;

        @BindView(R.id.ll_merchantdetailstory_photo)
        LinearLayout ll_merchantdetailstory_photo;

        @BindView(R.id.ll_merchantdetailstory_video)
        LinearLayout ll_merchantdetailstory_video;

        @BindView(R.id.ll_merchantdetailstory_vr)
        LinearLayout ll_merchantdetailstory_vr;

        @BindView(R.id.iv_merchantdetailstory_photo)
        ImageView iv_merchantdetailstory_photo;

        @BindView(R.id.iv_merchantdetailstory_videoplayer)
        ImageView iv_merchantdetailstory_videoplayer;

        @BindView(R.id.iv_merchantdetailstory_video)
        ImageView iv_merchantdetailstory_video;

        @BindView(R.id.vrpv_merchantdetailstory_vr)
        VrPanoramaView vrpv_merchantdetailstory_vr;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        @BindString(R.string.cloud_front_story_video)
        String storyVideoUrl;

        @BindString(R.string.cloud_front_story_vr360)
        String storyVr360Url;

        public MerchantDetailStoryFileViewHolder(MerchantDetailStoryPresenter merchantDetailStoryPresenter, File file, View view) {
            this.merchantDetailStoryPresenter = merchantDetailStoryPresenter;
            this.file = file;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.iv_merchantdetailstory_videoplayer)
        public void onClickPlayerButton() {
            merchantDetailStoryPresenter.onClickPlayerButton(file);
        }

        @OnClick(R.id.iv_merchantdetailstory_photo)
        public void onClickPhoto(){
            merchantDetailStoryPresenter.onClickPhoto(file);
        }

        @OnTouch(R.id.vrpv_merchantdetailstory_vr)
        public boolean onTouchVrView() {
            return true;
        }

    }

}
