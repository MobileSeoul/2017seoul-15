package com.stm.user.detail.merchant.fragment.photo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.user.detail.merchant.fragment.photo.presenter.MerchantDetailPhotoPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-02.
 */

public class MerchantDetailPhotoAdapter extends RecyclerView.Adapter<MerchantDetailPhotoAdapter.MerchantDetailPhotoViewHolder> {
    private MerchantDetailPhotoPresenter merchantDetailPhotoPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    public MerchantDetailPhotoAdapter(MerchantDetailPhotoPresenter merchantDetailPhotoPresenter, List<File> files, Context context, int layout) {
        this.merchantDetailPhotoPresenter = merchantDetailPhotoPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MerchantDetailPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MerchantDetailPhotoViewHolder merchantDetailPhotoViewHolder = new MerchantDetailPhotoViewHolder(merchantDetailPhotoPresenter, files, LayoutInflater.from(context).inflate(layout, parent, false));
        return merchantDetailPhotoViewHolder;
    }

    @Override
    public void onBindViewHolder(MerchantDetailPhotoViewHolder holder, int position) {
        File file = files.get(position);
        String fileExt = file.getExt();
        String fileName = file.getName();
        String url = holder.storyImageUrl + fileName + "." + fileExt;
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_merchantdetailphoto);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class MerchantDetailPhotoViewHolder extends RecyclerView.ViewHolder {
        private MerchantDetailPhotoPresenter merchantDetailPhotoPresenter;
        private List<File> files;

        @BindView(R.id.iv_merchantdetailphoto)
        ImageView iv_merchantdetailphoto;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        public MerchantDetailPhotoViewHolder(MerchantDetailPhotoPresenter merchantDetailPhotoPresenter, List<File> files, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.merchantDetailPhotoPresenter = merchantDetailPhotoPresenter;
            this.files = files;
        }

        @OnClick(R.id.iv_merchantdetailphoto)
        public void onClickPhoto() {
            int position = getAdapterPosition();
            File file = files.get(position);
            merchantDetailPhotoPresenter.onClickPhoto(file, position);
        }
    }
}
