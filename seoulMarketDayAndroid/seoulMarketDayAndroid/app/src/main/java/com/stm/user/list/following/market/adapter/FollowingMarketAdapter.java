package com.stm.user.list.following.market.adapter;

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
import com.stm.user.list.following.market.presenter.FollowingMarketPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowingMarketAdapter extends RecyclerView.Adapter<FollowingMarketAdapter.FollowingMarketViewHolder> {
    private FollowingMarketPresenter followingMarketPresenter;
    private List<Market> markets;
    private Context context;
    private int layout;

    public FollowingMarketAdapter(FollowingMarketPresenter followingMarketPresenter, List<Market> markets, Context context, int layout) {
        this.followingMarketPresenter = followingMarketPresenter;
        this.markets = markets;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public FollowingMarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FollowingMarketViewHolder followingMarketViewHolder = new FollowingMarketViewHolder(followingMarketPresenter, markets, LayoutInflater.from(context).inflate(layout, parent, false));
        return followingMarketViewHolder;
    }

    @Override
    public void onBindViewHolder(FollowingMarketViewHolder holder, int position) {
        Market market = markets.get(position);
        String name = market.getName();
        String address= market.getLotNumberAddress();
        String avatar = market.getAvatar();
        int followerCount = market.getFollowerCount();
        int merchantCount = market.getUserCount();
        boolean isFollowed = market.getFollowed();

        holder.tv_followingmarket_name.setText(name);
        holder.tv_followingmarket_address.setText(address);
        holder.tv_followingmarket_followercount.setText(String.valueOf(followerCount));
        holder.tv_followingmarket_merchantcount.setText(String.valueOf(merchantCount));

        if (avatar.trim().equals(DefaultFileFlag.MARKET_AVARTAR_NAME)) {
            avatar = holder.marketAvatarUrl + avatar;
        }

        Glide.with(context).load(avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_followingmarket_avatar);

        if(isFollowed){
            holder.iv_followmarket_followcancel.setVisibility(View.VISIBLE);
        } else {
            holder.iv_followmarket_follow.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return markets.size();
    }

    public void showFollow(FollowingMarketViewHolder holder){
        holder.iv_followmarket_follow.setVisibility(View.VISIBLE);
    }

    public void showFollowCancel(FollowingMarketViewHolder holder){
        holder.iv_followmarket_followcancel.setVisibility(View.VISIBLE);
    }

    public void goneFollow(FollowingMarketViewHolder holder){
        holder.iv_followmarket_follow.setVisibility(View.GONE);
    }

    public void goneFollowCancel(FollowingMarketViewHolder holder){
        holder.iv_followmarket_followcancel.setVisibility(View.GONE);
    }

    public void removeFollowingMarket(int position){
        this.markets.remove(position);
    }


    public static class FollowingMarketViewHolder extends RecyclerView.ViewHolder{
        private FollowingMarketPresenter followingMarketPresenter;
        private List<Market> markets;

        @BindView(R.id.iv_followingmarket_avatar)
        ImageView iv_followingmarket_avatar;

        @BindView(R.id.tv_followingmarket_name)
        TextView tv_followingmarket_name;

        @BindView(R.id.tv_followingmarket_address)
        TextView tv_followingmarket_address;

        @BindView(R.id.tv_followingmarket_followercount)
        TextView tv_followingmarket_followercount;

        @BindView(R.id.tv_followingmarket_merchantcount)
        TextView tv_followingmarket_merchantcount;

        @BindView(R.id.iv_followmarket_follow)
        ImageView iv_followmarket_follow;

        @BindView(R.id.iv_followmarket_followcancel)
        ImageView iv_followmarket_followcancel;

        @BindString(R.string.cloud_front_market_avatar)
        String marketAvatarUrl;

        public FollowingMarketViewHolder(FollowingMarketPresenter followingMarketPresenter, List<Market> markets,View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.followingMarketPresenter = followingMarketPresenter;
            this.markets = markets;
        }

        @OnClick(R.id.ll_followingmarket)
        public void onClickFollowingMarket(){
            int position = getAdapterPosition();
            Market market = markets.get(position);
            followingMarketPresenter.onClickFollowingMarket(market);
        }

        @OnClick(R.id.iv_followmarket_followcancel)
        public void onClickFollowCancel(){
            int position = getAdapterPosition();
            Market market = markets.get(position);
            followingMarketPresenter.onClickFollowCancel( market, position);
        }

    }
}
