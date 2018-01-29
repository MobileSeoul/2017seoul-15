package com.stm.main.fragment.main.base.adapter;

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
import com.stm.common.dto.LocationDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.main.fragment.main.base.presenter.MainFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-06-16.
 */

public class MarketByLocationAdapter extends RecyclerView.Adapter<MarketByLocationAdapter.MarketByLocationViewHolder> {
    private MainFragmentPresenter mainFragmentPresenter;
    private List<Market> markets;
    private Context context;
    private int layout;

    public MarketByLocationAdapter(MainFragmentPresenter mainFragmentPresenter, List<Market> markets, Context context, int layout) {
        this.mainFragmentPresenter = mainFragmentPresenter;
        this.markets = markets;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MarketByLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MarketByLocationViewHolder marketByLocationViewHolder = new MarketByLocationViewHolder(mainFragmentPresenter, markets, LayoutInflater.from(context).inflate(layout, parent, false));
        return marketByLocationViewHolder;
    }

    @Override
    public void onBindViewHolder(MarketByLocationViewHolder holder, int position) {
        Market market = markets.get(position);
        String name = market.getName();
        String lotNumberAddress = market.getLotNumberAddress();
        String avatar = market.getAvatar();
        int userCount = market.getUserCount();
        int storeCount = market.getStoreCount();
        double distance = market.getDistance();
        String distanceUnit = LocationDto.KILO_METER_UNIT;


        holder.includedMarketLayout.tv_market_name.setText(name);
        holder.includedMarketLayout.tv_market_address.setText(lotNumberAddress);
        holder.includedMarketLayout.tv_market_merchant_count.setText(userCount + Market.MEMBER_UNIT);
        holder.includedMarketLayout.tv_market_store_count.setText(storeCount + Market.STORE_UNIT);

        if (distance < 1) {
            distance *= 1000;
            distanceUnit = LocationDto.METER_UNIT;
        }
        holder.tv_market_distance.setText(distance + distanceUnit);

        if (avatar.trim().equals(DefaultFileFlag.MARKET_AVARTAR_NAME)) {
            avatar = holder.marketAvatarUrl + avatar;
        }
        Glide.with(context).load(avatar).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.includedMarketLayout.iv_market_avatar);
    }

    @Override
    public int getItemCount() {
        return markets.size();
    }


    public static class MarketByLocationViewHolder extends RecyclerView.ViewHolder {
        private MainFragmentPresenter mainFragmentPresenter;
        private List<Market> markets;

        IncludedMarketLayout includedMarketLayout;

        @BindView(R.id.ll_market_info_with_distance)
        LinearLayout ll_market_info_with_distance;

        @BindView(R.id.ll_market_distance)
        LinearLayout ll_market_distance;

        @BindView(R.id.tv_market_distance)
        TextView tv_market_distance;

        @BindView(R.id.in_item_fragmentmain_market)
        View in_item_fragmentmain_market;

        @BindString(R.string.cloud_front_market_avatar)
        String marketAvatarUrl;


        public MarketByLocationViewHolder(MainFragmentPresenter mainFragmentPresenter, List<Market> markets, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ll_market_distance.bringToFront();
            includedMarketLayout = new IncludedMarketLayout();
            ButterKnife.bind(includedMarketLayout, in_item_fragmentmain_market);

            this.mainFragmentPresenter = mainFragmentPresenter;
            this.markets = markets;
        }

        @OnClick(R.id.ll_market_info_with_distance)
        public void onClickMarket() {
            int position = getAdapterPosition();
            Market market = markets.get(position);
            mainFragmentPresenter.onClickMarket(market);
        }

        static class IncludedMarketLayout {
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
        }
    }
}
