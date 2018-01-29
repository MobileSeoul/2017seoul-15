package com.stm.user.detail.merchant.fragment.follower.adapter;

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
import com.stm.common.dao.User;
import com.stm.user.detail.merchant.fragment.follower.presenter.MerchantDetailFollowerPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-04.
 */

public class MerchantDetailFollowerAdapter extends RecyclerView.Adapter<MerchantDetailFollowerAdapter.MerchantDetailFollowerViewHolder> {
    private MerchantDetailFollowerPresenter merchantDetailFollowerPresenter;
    private List<User> followers;
    private Context context;
    private int layout;

    public MerchantDetailFollowerAdapter(MerchantDetailFollowerPresenter merchantDetailFollowerPresenter, List<User> followers, Context context, int layout) {
        this.merchantDetailFollowerPresenter = merchantDetailFollowerPresenter;
        this.followers = followers;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MerchantDetailFollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MerchantDetailFollowerViewHolder merchantDetailFollowerViewHolder = new MerchantDetailFollowerViewHolder(merchantDetailFollowerPresenter, followers, LayoutInflater.from(context).inflate(layout, parent, false));
        return merchantDetailFollowerViewHolder;
    }

    @Override
    public void onBindViewHolder(MerchantDetailFollowerViewHolder holder, int position) {
        User follower = followers.get(position);

        String name = follower.getName();
        String avatar = holder.userAvatarUrl + follower.getAvatar();

        holder.tv_merchantdetailfollower_name.setText(name);
        Glide.with(context).load(avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_merchantdetailfollower_avatar);

    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public static class MerchantDetailFollowerViewHolder extends RecyclerView.ViewHolder {
        private MerchantDetailFollowerPresenter merchantDetailFollowerPresenter;
        private List<User> followers;

        @BindView(R.id.ll_merchantdetailfollower)
        LinearLayout ll_merchantdetailfollower;

        @BindView(R.id.iv_merchantdetailfollower_avatar)
        ImageView iv_merchantdetailfollower_avatar;

        @BindView(R.id.tv_merchantdetailfollower_name)
        TextView tv_merchantdetailfollower_name;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        public MerchantDetailFollowerViewHolder(MerchantDetailFollowerPresenter merchantDetailFollowerPresenter, List<User> followers, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.merchantDetailFollowerPresenter = merchantDetailFollowerPresenter;
            this.followers = followers;
        }

        @OnClick(R.id.ll_merchantdetailfollower)
        public void onClickFollower(){
            int position = getAdapterPosition();
            User user = followers.get(position);
            merchantDetailFollowerPresenter.onClickFollower(user);
        }

    }
}


