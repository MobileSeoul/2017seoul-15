package com.stm.main.fragment.main.search.fragment.user.adapter;

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
import com.stm.common.dao.User;
import com.stm.main.fragment.main.search.fragment.user.presenter.SearchUserPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.SearchUserViewHolder> {
    private SearchUserPresenter searchMerchantPresent;
    private List<User> users;
    private Context context;
    private int layout;

    public SearchUserAdapter(SearchUserPresenter searchMerchantPresent, List<User> users, Context context, int layout) {
        this.searchMerchantPresent = searchMerchantPresent;
        this.users = users;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public SearchUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchUserViewHolder searchUserViewHolder = new SearchUserViewHolder(searchMerchantPresent, users, LayoutInflater.from(context).inflate(layout, parent, false));
        return searchUserViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchUserViewHolder holder, int position) {
        User user = users.get(position);
        String name = user.getName();
        String avatar = user.getAvatar();
        Market market = user.getMarket();

        holder.tv_searchmerchant_name.setText(name);
        Glide.with(context).load(holder.userAvatarUrl + avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_searchmerchant_avatar);

        if (market != null) {
            String marketName = market.getName();
            holder.tv_searchuser_market.setText(marketName);
        } else {
            holder.tv_searchuser_market.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class SearchUserViewHolder extends RecyclerView.ViewHolder {
        private SearchUserPresenter searchMerchantPresent;
        private List<User> users;

        @BindView(R.id.iv_searchuser_avatar)
        ImageView iv_searchmerchant_avatar;

        @BindView(R.id.tv_searchuser_name)
        TextView tv_searchmerchant_name;

        @BindView(R.id.tv_searchuser_market)
        TextView tv_searchuser_market;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        public SearchUserViewHolder(SearchUserPresenter searchMerchantPresent, List<User> users, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.searchMerchantPresent = searchMerchantPresent;
            this.users = users;
        }

        @OnClick(R.id.ll_searchuser)
        public void onClickUser(){
            int position = getAdapterPosition();
            User user = users.get(position);
            searchMerchantPresent.onClickUser(user);
        }

    }
}
