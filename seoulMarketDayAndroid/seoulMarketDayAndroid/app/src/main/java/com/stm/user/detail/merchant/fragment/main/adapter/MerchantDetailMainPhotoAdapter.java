package com.stm.user.detail.merchant.fragment.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.user.detail.merchant.fragment.main.presenter.MerchantDetailMainPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-07-31.
 */

public class MerchantDetailMainPhotoAdapter extends RecyclerView.Adapter<MerchantDetailMainPhotoAdapter.MerchantDetailMainPhotoViewHolder> {
    private MerchantDetailMainPresenter merchantDetailMainPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    public MerchantDetailMainPhotoAdapter(MerchantDetailMainPresenter merchantDetailMainPresenter, List<File> files, Context context, int layout) {
        this.merchantDetailMainPresenter = merchantDetailMainPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MerchantDetailMainPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MerchantDetailMainPhotoViewHolder merchantDetailMainPhotoViewHolder = new MerchantDetailMainPhotoViewHolder(merchantDetailMainPresenter, files, LayoutInflater.from(context).inflate(layout, parent, false));
        return merchantDetailMainPhotoViewHolder;
    }

    @Override
    public void onBindViewHolder(MerchantDetailMainPhotoViewHolder holder, int position) {
        File file = files.get(position);
        String fileExt = file.getExt();
        String fileName = file.getName();
        String url = holder.storyImageUrl + fileName + "." + fileExt;
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_merchantdetailmain_photo);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class MerchantDetailMainPhotoViewHolder extends RecyclerView.ViewHolder {
        private MerchantDetailMainPresenter merchantDetailMainPresenter;
        private List<File> files;

        @BindView(R.id.iv_merchantdetailmain_photo)
        ImageView iv_merchantdetailmain_photo;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        public MerchantDetailMainPhotoViewHolder(MerchantDetailMainPresenter merchantDetailMainPresenter, List<File> files, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.merchantDetailMainPresenter = merchantDetailMainPresenter;
            this.files = files;
        }

        @OnClick({R.id.ll_merchantdetailmain_photo, R.id.iv_merchantdetailmain_photo})
        public void onClickPhotoLayout() {
            int position = getAdapterPosition();
            File file = files.get(position);
            merchantDetailMainPresenter.onClickPhotoLayout(file, position);
        }
    }
}
