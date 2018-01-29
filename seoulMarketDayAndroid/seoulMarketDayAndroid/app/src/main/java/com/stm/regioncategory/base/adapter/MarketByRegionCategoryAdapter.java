package com.stm.regioncategory.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.Market;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.regioncategory.base.presenter.RegionCategoryPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-19.
 */

public class MarketByRegionCategoryAdapter extends RecyclerView.Adapter<MarketByRegionCategoryAdapter.RegionCategoryWithMarketViewHolder> {
    private RegionCategoryPresenter regionCategoryPresenter;
    private List<Market> markets;
    private Context context;
    private int layout;

    public MarketByRegionCategoryAdapter(RegionCategoryPresenter regionCategoryPresenter, List<Market> markets, Context context, int layout) {
        this.regionCategoryPresenter = regionCategoryPresenter;
        this.markets = markets;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public RegionCategoryWithMarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layout, parent, false);
        RegionCategoryWithMarketViewHolder regionCategoryWithMarketViewHolder = new RegionCategoryWithMarketViewHolder(regionCategoryPresenter, markets, itemView);
        return regionCategoryWithMarketViewHolder;
    }

    @Override
    public void onBindViewHolder(RegionCategoryWithMarketViewHolder holder, int position) {
        Market market = markets.get(position);
        String name = market.getName();
        String avatar = market.getAvatar();
        String address = market.getRoadAddress();
        int storeCount = market.getStoreCount();
        int userCount = market.getUserCount();

        if (avatar.trim().equals(DefaultFileFlag.MARKET_AVARTAR_NAME)) {
            avatar = holder.marketAvatarUrl + avatar;
        }
        Glide.with(context).load(avatar).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_market_avatar);

        holder.tv_market_name.setText(name);
        holder.tv_market_merchant_count.setText(userCount + Market.MEMBER_UNIT);
        holder.tv_market_store_count.setText(storeCount + Market.STORE_UNIT);
        holder.tv_market_address.setText(address);
    }

    @Override
    public int getItemCount() {
        return markets.size();
    }

    public static class RegionCategoryWithMarketViewHolder extends RecyclerView.ViewHolder {
        private RegionCategoryPresenter regionCategoryPresenter;
        private List<Market> markets;

        @BindView(R.id.ll_market_info)
        LinearLayout ll_market_info;

        @BindView(R.id.iv_market_avatar)
        ImageView iv_market_avatar;

        @BindView(R.id.tv_market_store_count)
        TextView tv_market_store_count;

        @BindView(R.id.tv_market_merchant_count)
        TextView tv_market_merchant_count;

        @BindView(R.id.tv_market_name)
        TextView tv_market_name;

        @BindView(R.id.tv_market_address)
        TextView tv_market_address;

        @BindString(R.string.cloud_front_market_avatar)
        String marketAvatarUrl;

        public RegionCategoryWithMarketViewHolder(RegionCategoryPresenter regionCategoryPresenter, List<Market> markets, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.markets = markets;
            this.regionCategoryPresenter = regionCategoryPresenter;
        }

        @OnClick(R.id.ll_market_info)
        public void onClickMarket() {
            int position = getAdapterPosition();
            Market market = markets.get(position);
            regionCategoryPresenter.onClickMarket(market);
        }
    }

}
