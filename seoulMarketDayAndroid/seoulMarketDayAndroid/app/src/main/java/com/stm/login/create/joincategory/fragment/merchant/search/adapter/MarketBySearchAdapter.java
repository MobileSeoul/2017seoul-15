package com.stm.login.create.joincategory.fragment.merchant.search.adapter;

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
import com.stm.login.create.joincategory.fragment.merchant.search.presenter.SearchMarketPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public class MarketBySearchAdapter extends RecyclerView.Adapter<MarketBySearchAdapter.SearchMarketViewHolder> {
    private SearchMarketPresenter searchMarketPresenter;
    private List<Market> markets;
    private Context context;
    private int layout;

    public MarketBySearchAdapter(SearchMarketPresenter searchMarketPresenter, List<Market> markets, Context context, int layout) {
        this.searchMarketPresenter = searchMarketPresenter;
        this.markets = markets;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public SearchMarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchMarketViewHolder searchMarketViewHolder = new SearchMarketViewHolder(LayoutInflater.from(context).inflate(layout, parent, false));
        return searchMarketViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchMarketViewHolder holder, int position) {
        Market market = markets.get(position);
        String name = market.getName();
        String address = market.getLotNumberAddress();
        String avatar = market.getAvatar();

        holder.tv_searchmarket_address.setText(address);
        holder.tv_searchmarket_name.setText(name);

        if (avatar.trim().equals(DefaultFileFlag.MARKET_AVARTAR_NAME)) {
            avatar = holder.marketAvatarUrl + avatar;
        }
        Glide.with(context).load(avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_searchmarket_avatar);
    }

    @Override
    public int getItemCount() {
        return markets.size();
    }

    public class SearchMarketViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_searchmarket_avatar)
        ImageView iv_searchmarket_avatar;

        @BindView(R.id.tv_searchmarket_name)
        TextView tv_searchmarket_name;

        @BindView(R.id.tv_searchmarket_address)
        TextView tv_searchmarket_address;

        @BindString(R.string.cloud_front_market_avatar)
        String marketAvatarUrl;

        public SearchMarketViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_searchmarket)
        public void onClickItem() {
            Market market = markets.get(getAdapterPosition());
            searchMarketPresenter.onClickItem(market);
        }
    }
}
