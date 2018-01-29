package com.stm.market.fragment.photo.adapter;

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
import com.stm.market.fragment.photo.presenter.MarketPhotoPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketPhotoAdapter extends RecyclerView.Adapter<MarketPhotoAdapter.MarketPhotoViewHolder> {
    private MarketPhotoPresenter marketPhotoPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    public MarketPhotoAdapter(MarketPhotoPresenter marketPhotoPresenter, List<File> files, Context context, int layout) {
        this.marketPhotoPresenter = marketPhotoPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MarketPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MarketPhotoViewHolder marketPhotoViewHolder = new MarketPhotoViewHolder(marketPhotoPresenter, files, LayoutInflater.from(context).inflate(layout, parent, false));
        return marketPhotoViewHolder;
    }

    @Override
    public void onBindViewHolder(MarketPhotoViewHolder holder, int position) {
        File file = files.get(position);
        String fileExt = file.getExt();
        String fileName = file.getName();
        String url = holder.storyImageUrl + fileName + "." + fileExt;
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_marketphoto);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class MarketPhotoViewHolder extends RecyclerView.ViewHolder {
        private MarketPhotoPresenter marketPhotoPresenter;
        private List<File> files;

        @BindView(R.id.iv_marketphoto)
        ImageView iv_marketphoto;

        @BindString(R.string.cloud_front_story_image)
        String storyImageUrl;

        public MarketPhotoViewHolder(MarketPhotoPresenter marketPhotoPresenter, List<File> files, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.marketPhotoPresenter = marketPhotoPresenter;
            this.files = files;
        }

        @OnClick(R.id.iv_marketphoto)
        public void onClickPhoto(){
            int position = getAdapterPosition();
            File file = files.get(position);
            marketPhotoPresenter.onClickPhoto(file, position);
        }
    }
}
