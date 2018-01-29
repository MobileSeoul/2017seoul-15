package com.stm.market.fragment.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.market.fragment.main.presenter.MarketMainPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by ㅇㅇ on 2017-08-23.
 */

public class MarketMainPhotoAdapter extends RecyclerView.Adapter<MarketMainPhotoAdapter.MarketMainPhotoViewHolder> {
    private MarketMainPresenter marketMainPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    public MarketMainPhotoAdapter(MarketMainPresenter marketMainPresenter, List<File> files, Context context, int layout) {
        this.marketMainPresenter = marketMainPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MarketMainPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MarketMainPhotoViewHolder marketMainPhotoViewHolder = new MarketMainPhotoViewHolder(marketMainPresenter, files, LayoutInflater.from(context).inflate(layout, parent, false));
        return marketMainPhotoViewHolder;
    }

    @Override
    public void onBindViewHolder(MarketMainPhotoViewHolder holder, int position) {
        File file = files.get(position);
        String fileName = file.getName();
        String fileExt = file.getExt();
        String url = fileName + "." + fileExt;

        Glide.with(context).load(holder.storyImageUrl + url).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_marketmain_photo);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class MarketMainPhotoViewHolder extends RecyclerView.ViewHolder {
        private MarketMainPresenter marketMainPresenter;
        private List<File> files;

        @BindView(R.id.iv_marketmain_photo)
        ImageView iv_marketmain_photo;

        @BindView(R.id.ll_marketmain_photo)
        LinearLayout ll_marketmain_photo;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        public MarketMainPhotoViewHolder(MarketMainPresenter marketMainPresenter, List<File> files, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.marketMainPresenter = marketMainPresenter;
            this.files = files;
        }

        @Optional
        @OnClick({R.id.ll_marketmain_photo, R.id.iv_merchantdetailmain_photo})
        public void onClickPhotoLayout(){
            int position = getAdapterPosition();
            File file = files.get(position);
            marketMainPresenter.onClickPhotoLayout(file, position);
        }

    }
}
