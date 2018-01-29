package com.stm.user.detail.merchant.fragment.video.adapter;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.CalculateDateUtil;
import com.stm.user.detail.merchant.fragment.video.presenter.MerchantDetailVideoPresenter;

import java.io.IOException;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-04.
 */

public class MerchantDetailVideoAdapter extends RecyclerView.Adapter<MerchantDetailVideoAdapter.MerchantDetailVideoViewHolder> {
    private MerchantDetailVideoPresenter merchantDetailVideoPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    @BindString(R.string.app_name)
    String app_name;

    public MerchantDetailVideoAdapter(MerchantDetailVideoPresenter merchantDetailVideoPresenter, List<File> files, Context context, int layout) {
        this.merchantDetailVideoPresenter = merchantDetailVideoPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MerchantDetailVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MerchantDetailVideoViewHolder merchantDetailVideoViewHolder = new MerchantDetailVideoViewHolder(merchantDetailVideoPresenter, files, LayoutInflater.from(context).inflate(layout, parent, false));
        return merchantDetailVideoViewHolder;
    }

    @Override
    public void onBindViewHolder(final MerchantDetailVideoViewHolder holder, final int position) {
        File file = files.get(position);
        int hits = file.getHits();
        String created_at = file.getCreatedAt();
        String fileName = file.getName();
        String thumbnailUrl = holder.storyVideoUrl + fileName + "." + DefaultFileFlag.VIDEO_THUMBNAIL_EXT;

        Glide.with(context).load(thumbnailUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_merchantdetailvideo);
        holder.tv_merchantdetailvideo_date.setText((CalculateDateUtil.getCalculateDateByDateTime(created_at)));
        holder.tv_merchantdetailvideo_hits.setText(hits + "");
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class MerchantDetailVideoViewHolder extends RecyclerView.ViewHolder {
        private MerchantDetailVideoPresenter merchantDetailVideoPresenter;
        private List<File> files;

        @BindView(R.id.iv_merchantdetailvideo)
        ImageView iv_merchantdetailvideo;

        @BindView(R.id.tv_merchantdetailvideo_hits)
        TextView tv_merchantdetailvideo_hits;

        @BindView(R.id.tv_merchantdetailvideo_date)
        TextView tv_merchantdetailvideo_date;

        @BindString(R.string.cloud_front_story_video)
        String storyVideoUrl;

        public MerchantDetailVideoViewHolder(MerchantDetailVideoPresenter merchantDetailVideoPresenter, List<File> files, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.merchantDetailVideoPresenter = merchantDetailVideoPresenter;
            this.files = files;
        }

        @OnClick(R.id.ll_merchantdetailvideo)
        public void onClickVideo() {
            int position = getAdapterPosition();
            File file = files.get(position);

            merchantDetailVideoPresenter.onClickVideo(file, position);
        }

    }
}
