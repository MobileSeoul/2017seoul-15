package com.stm.market.fragment.video.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.CalculateDateUtil;
import com.stm.market.fragment.video.presenter.MarketVideoPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketVideoAdapter extends RecyclerView.Adapter<MarketVideoAdapter.MarketVideoViewHolder> {
    private MarketVideoPresenter marketVideoPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    public MarketVideoAdapter(MarketVideoPresenter marketVideoPresenter, List<File> files, Context context, int layout) {
        this.marketVideoPresenter = marketVideoPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MarketVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MarketVideoViewHolder marketVideoViewHolder = new MarketVideoViewHolder(marketVideoPresenter, files, LayoutInflater.from(context).inflate(layout, parent, false));
        return marketVideoViewHolder;
    }

    @Override
    public void onBindViewHolder(MarketVideoViewHolder holder, int position) {
        File file = files.get(position);
        int hits = file.getHits();
        String created_at = file.getCreatedAt();
        String fileName = file.getName();
        String thumbnailUrl = holder.storyVideoUrl + fileName + "." + DefaultFileFlag.VIDEO_THUMBNAIL_EXT;

        Glide.with(context).load(thumbnailUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_marketvideo);
        holder.tv_marketvideo_date.setText((CalculateDateUtil.getCalculateDateByDateTime(created_at)));
        holder.tv_marketvideo_hits.setText(String.valueOf(hits));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class MarketVideoViewHolder extends RecyclerView.ViewHolder {
        private MarketVideoPresenter marketVideoPresenter;
        private List<File> files;

        @BindView(R.id.iv_marketvideo)
        ImageView iv_marketvideo;

        @BindView(R.id.tv_marketvideo_hits)
        TextView tv_marketvideo_hits;

        @BindView(R.id.tv_marketvideo_date)
        TextView tv_marketvideo_date;

        @BindString(R.string.cloud_front_story_video)
        String storyVideoUrl;

        public MarketVideoViewHolder(MarketVideoPresenter marketVideoPresenter, List<File> files, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.marketVideoPresenter = marketVideoPresenter;
            this.files = files;

        }

        @OnClick(R.id.ll_marketvideo)
        public void onClickVideo() {
            int position = getAdapterPosition();
            File file = files.get(position);

            marketVideoPresenter.onClickVideo(file, position);
        }
    }
}
