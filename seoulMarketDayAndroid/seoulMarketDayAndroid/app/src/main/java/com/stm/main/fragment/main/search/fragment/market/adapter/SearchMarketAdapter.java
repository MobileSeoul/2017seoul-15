package com.stm.main.fragment.main.search.fragment.market.adapter;

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
import com.stm.common.dao.Market;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.main.fragment.main.search.fragment.market.presenter.SearchMarketPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-09-04.
 */

public class SearchMarketAdapter extends RecyclerView.Adapter<SearchMarketAdapter.SearchMarketViewHolder> {
    private SearchMarketPresenter searchMarketPresenter;
    private List<Market> markets;
    private Context context;
    private int layout;

    public SearchMarketAdapter(SearchMarketPresenter searchMarketPresenter, List<Market> markets, Context context, int layout) {
        this.searchMarketPresenter = searchMarketPresenter;
        this.markets = markets;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public SearchMarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchMarketViewHolder searchMarketViewHolder = new SearchMarketViewHolder(searchMarketPresenter, markets, LayoutInflater.from(context).inflate(layout, parent, false));
        return searchMarketViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchMarketViewHolder holder, int position) {
        Market market = markets.get(position);
        String name = market.getName();
        String address = market.getLotNumberAddress();
        String avatar = market.getAvatar();

        holder.tv_searchmarket_name.setText(name);
        holder.tv_searchmarket_address.setText(address);

        if (avatar.trim().equals(DefaultFileFlag.MARKET_AVARTAR_NAME)) {
            avatar = holder.marketAvatarUrl + avatar;
        }
        Glide.with(context).load(avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_searchmarket_avatar);
    }

    @Override
    public int getItemCount() {
        return markets.size();
    }

    public static class SearchMarketViewHolder extends RecyclerView.ViewHolder{
        private SearchMarketPresenter searchMarketPresenter;
        private List<Market> markets;

        @BindView(R.id.iv_searchmarket_avatar)
        ImageView iv_searchmarket_avatar;

        @BindView(R.id.tv_searchmarket_name)
        TextView tv_searchmarket_name;

        @BindView(R.id.tv_searchmarket_address)
        TextView tv_searchmarket_address;

        @BindString(R.string.cloud_front_market_avatar)
        String marketAvatarUrl;

        public SearchMarketViewHolder(SearchMarketPresenter searchMarketPresenter, List<Market> markets,View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.searchMarketPresenter = searchMarketPresenter;
            this.markets = markets;
        }

        @OnClick(R.id.ll_searchmarket)
        public void onClickMarket(){
            int position = getAdapterPosition();
            Market market = markets.get(position);
            searchMarketPresenter.onClickMarket(market);
        }
    }
}
