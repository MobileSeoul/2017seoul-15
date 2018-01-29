package com.stm.user.list.following.merchant.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.User;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.user.list.following.merchant.presenter.FollowingMerchantPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowingMerchantAdapter extends RecyclerView.Adapter<FollowingMerchantAdapter.FollowingMerchantViewHolder> {
    private FollowingMerchantPresenter followingMerchantPresenter;
    private List<User> users;
    private Context context;
    private int layout;

    public FollowingMerchantAdapter(FollowingMerchantPresenter followingMerchantPresenter, List<User> users, Context context, int layout) {
        this.followingMerchantPresenter = followingMerchantPresenter;
        this.users = users;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public FollowingMerchantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FollowingMerchantViewHolder followingMerchantViewHolder = new FollowingMerchantViewHolder(followingMerchantPresenter, users, LayoutInflater.from(context).inflate(layout, parent, false));
        return followingMerchantViewHolder;
    }

    @Override
    public void onBindViewHolder(FollowingMerchantViewHolder holder, int position) {
        User user = users.get(position);
        String name  = user.getName();
        String avatar = user.getAvatar();
        boolean isFollowed = user.getFollowed();

        holder.tv_followingmerchant_name.setText(name);
        Glide.with(context).load(holder.userAvatarUrl + avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_followingmerchant_avatar);

        if(isFollowed){
            holder.iv_followingmerchant_followcancel.setVisibility(View.VISIBLE);
        } else {
            holder.iv_followingmerchant_follow.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void removeFollowingMerchant(int position){
        this.users.remove(position);
    }

    public static class FollowingMerchantViewHolder extends RecyclerView.ViewHolder {
        private FollowingMerchantPresenter followingMerchantPresenter;
        private List<User> users;

        @BindView(R.id.iv_followingmerchant_avatar)
        ImageView iv_followingmerchant_avatar;

        @BindView(R.id.tv_followingmerchant_name)
        TextView tv_followingmerchant_name;

        @BindView(R.id.iv_followingmerchant_follow)
        ImageView iv_followingmerchant_follow;

        @BindView(R.id.iv_followingmerchant_followcancel)
        ImageView iv_followingmerchant_followcancel;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        public FollowingMerchantViewHolder(FollowingMerchantPresenter followingMerchantPresenter, List<User> users, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.followingMerchantPresenter = followingMerchantPresenter;
            this.users = users;
        }

        @OnClick(R.id.ll_followingmerchant)
        public void onClickFollowingMerchant(){
            int position = getAdapterPosition();
            User user = users.get(position);
            followingMerchantPresenter.onClickFollowingMerchant(user);
        }

        @OnClick(R.id.iv_followingmerchant_followcancel)
        public void onClickFollowCancel(){
            int position = getAdapterPosition();
            User merchant = users.get(position);
            followingMerchantPresenter.onClickFollowCancel(merchant, position);
        }
    }
}
