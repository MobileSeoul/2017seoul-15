package com.stm.user.detail.merchant.fragment.main.adapter;

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
import com.stm.user.detail.merchant.fragment.main.presenter.MerchantDetailMainPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by ㅇㅇ on 2017-07-25.
 */

public class MerchantDetailMainFileAdapter extends PagerAdapter {
    private MerchantDetailMainFileViewHolder merchantDetailMainFileViewHolder;
    private MerchantDetailMainPresenter merchantDetailMainPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    public MerchantDetailMainFileAdapter(MerchantDetailMainPresenter merchantDetailMainPresenter, List<File> files, Context context, int layout) {
        this.merchantDetailMainPresenter = merchantDetailMainPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layout, container, false);
        File file = files.get(position);
        int type = file.getType();

        merchantDetailMainFileViewHolder = new MerchantDetailMainFileViewHolder(merchantDetailMainPresenter, file, view);
        view.setTag(merchantDetailMainFileViewHolder);

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            String uri = merchantDetailMainFileViewHolder.storyImageUrl + file.getName() + "." + file.getExt();

            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyphoto.setVisibility(View.VISIBLE);
            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyvideo.setVisibility(View.GONE);
            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyvr.setVisibility(View.GONE);

            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyphoto.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(merchantDetailMainFileViewHolder.iv_merchantdetailmain_storyphoto);
        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            String uri = merchantDetailMainFileViewHolder.storyVideoUrl + file.getName() + "." + file.getExt();

            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyphoto.setVisibility(View.GONE);
            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyvideo.setVisibility(View.VISIBLE);
            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyvr.setVisibility(View.GONE);

            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyvideo.bringToFront();
            merchantDetailMainFileViewHolder.iv_merchantdetailmain_storyvideoplayer.bringToFront();
            Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into( merchantDetailMainFileViewHolder.iv_merchantdetailmain_storyvideo);

        }

        if (type == DefaultFileFlag.VR360_TYPE) {
            String uri = merchantDetailMainFileViewHolder.storyVr360Url + file.getName() + "." + file.getExt();
            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyphoto.setVisibility(View.GONE);
            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyvideo.setVisibility(View.GONE);
            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyvr.setVisibility(View.VISIBLE);

            merchantDetailMainFileViewHolder.ll_merchantdetailmain_storyvr.bringToFront();
            Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType = VrPanoramaView.Options.TYPE_MONO;
                    merchantDetailMainFileViewHolder.vrpv_merchantdetailmain_storyvr.setInfoButtonEnabled(false);
                    merchantDetailMainFileViewHolder.vrpv_merchantdetailmain_storyvr.loadImageFromBitmap(resource, options);
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

    public static class MerchantDetailMainFileViewHolder {
        private MerchantDetailMainPresenter merchantDetailMainPresenter;
        private File file;

        @BindView(R.id.ll_merchantdetailmain_storyphoto)
        LinearLayout ll_merchantdetailmain_storyphoto;

        @BindView(R.id.ll_merchantdetailmain_storyvideo)
        LinearLayout ll_merchantdetailmain_storyvideo;

        @BindView(R.id.ll_merchantdetailmain_storyvr)
        LinearLayout ll_merchantdetailmain_storyvr;

        @BindView(R.id.iv_merchantdetailmain_storyphoto)
        ImageView iv_merchantdetailmain_storyphoto;

        @BindView(R.id.iv_merchantdetailmain_storyvideo)
        ImageView iv_merchantdetailmain_storyvideo;

        @BindView(R.id.iv_merchantdetailmain_storyvideoplayer)
        ImageView iv_merchantdetailmain_storyvideoplayer;

        @BindView(R.id.vrpv_merchantdetailmain_storyvr)
        VrPanoramaView vrpv_merchantdetailmain_storyvr;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        @BindString(R.string.cloud_front_story_video)
        String storyVideoUrl;

        @BindString(R.string.cloud_front_story_vr360)
        String storyVr360Url;

        public MerchantDetailMainFileViewHolder(MerchantDetailMainPresenter merchantDetailMainPresenter, File file, View view) {
            this.merchantDetailMainPresenter = merchantDetailMainPresenter;
            this.file = file;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.iv_merchantdetailmain_storyvideoplayer)
        public void onClickPlayerButton() {
            merchantDetailMainPresenter.onClickPlayerButton(file);
        }

        @OnClick(R.id.iv_merchantdetailmain_storyphoto)
        public void onClickPhoto(){
            merchantDetailMainPresenter.onClickPhoto(file);
        }

        @OnTouch(R.id.vrpv_merchantdetailmain_storyvr)
        public boolean onTouchVrView() {
            return true;
        }

    }

}
