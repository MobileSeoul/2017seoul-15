package com.stm.market.fragment.merchant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.flag.UserLevelFlag;
import com.stm.market.fragment.merchant.presenter.MarketMerchantPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-07-07.
 */

public class MarketMerchantAdapter extends RecyclerView.Adapter<MarketMerchantAdapter.MerchantViewHolder> {
    private MarketMerchantPresenter marketMerchantPresenter;
    private List<User> users;
    private Context context;
    private int layout;
    private User user;

    public MarketMerchantAdapter(MarketMerchantPresenter marketMerchantPresenter, List<User> users, Context context, int layout, User user) {
        this.marketMerchantPresenter = marketMerchantPresenter;
        this.users = users;
        this.context = context;
        this.layout = layout;
        this.user = user;
    }

    @Override
    public MerchantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MerchantViewHolder merchantViewHoler = new MerchantViewHolder(marketMerchantPresenter, users, LayoutInflater.from(context).inflate(layout, parent, false));
        return merchantViewHoler;
    }

    @Override
    public void onBindViewHolder(MerchantViewHolder holder, int position) {
        User merchant = users.get(position);

        if (user != null) {
            int userLevel = user.getLevel();
            if (userLevel == UserLevelFlag.MERCHANT) {
                holder.btn_merchant_follow.setVisibility(View.GONE);
                holder.ib_merchant_detail.setVisibility(View.VISIBLE);
            } else {
                Boolean isFollowed = merchant.getFollowed();
                if (isFollowed != null && isFollowed) {
                    holder.btn_merchant_follow.setVisibility(View.GONE);
                    holder.btn_merchant_follow_cancel.setVisibility(View.VISIBLE);
                } else {
                    holder.btn_merchant_follow.setVisibility(View.VISIBLE);
                    holder.btn_merchant_follow_cancel.setVisibility(View.GONE);
                }
                holder.ib_merchant_detail.setVisibility(View.GONE);
            }
        }

        String name = merchant.getName();
        String avatar = holder.userAvatarUrl + merchant.getAvatar();

        holder.tv_merchant_name.setText(name);
        Glide.with(context).load(avatar).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_merchant_avatar);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MerchantViewHolder extends RecyclerView.ViewHolder {
        private MarketMerchantPresenter marketMerchantPresenter;
        private List<User> users;

        @BindView(R.id.tv_merchant_name)
        TextView tv_merchant_name;

        @BindView(R.id.iv_merchant_avatar)
        ImageView iv_merchant_avatar;

        @BindView(R.id.fl_merchant_follow)
        FrameLayout fl_merchant_follow;

        @BindView(R.id.btn_merchant_follow)
        Button btn_merchant_follow;

        @BindView(R.id.btn_merchant_follow_cancel)
        Button btn_merchant_follow_cancel;

        @BindView(R.id.ib_merchant_detail)
        ImageButton ib_merchant_detail;


        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        public MerchantViewHolder(MarketMerchantPresenter marketMerchantPresenter, List<User> users, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.marketMerchantPresenter = marketMerchantPresenter;
            this.users = users;

        }

        @OnClick(R.id.ll_merchant)
        public void onClickMerchant() {
            int position = getAdapterPosition();
            User user = users.get(position);

            marketMerchantPresenter.onClickMerchant(user, position);
        }

        @OnClick(R.id.btn_merchant_follow)
        public void onClickFollow() {
            int position = getAdapterPosition();
            User user = users.get(position);

            marketMerchantPresenter.onClickFollow(user, position);
        }

        @OnClick(R.id.btn_merchant_follow_cancel)
        public void onClickFollowCancel() {
            int position = getAdapterPosition();
            User user = users.get(position);

            marketMerchantPresenter.onClickFollowCancel(user, position);
        }
    }
}
