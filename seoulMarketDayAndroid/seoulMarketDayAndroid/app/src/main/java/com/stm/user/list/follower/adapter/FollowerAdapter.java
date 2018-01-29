package com.stm.user.list.follower.adapter;

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
import com.stm.user.list.follower.presenter.FollowerPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder> {
    private FollowerPresenter followerPresenter;
    private List<User> users;
    private Context context;
    private int layout;

    public FollowerAdapter(FollowerPresenter followerPresenter, List<User> users, Context context, int layout) {
        this.followerPresenter = followerPresenter;
        this.users = users;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FollowerViewHolder followerViewHolder = new FollowerViewHolder(followerPresenter, users, LayoutInflater.from(context).inflate(layout, parent, false));
        return followerViewHolder;
    }

    @Override
    public void onBindViewHolder(FollowerViewHolder holder, int position) {
        User user = users.get(position);
        String name = user.getName();
        String avatar = user.getAvatar();

        holder.tv_follower_name.setText(name);
        Glide.with(context).load(holder.userAvatarUrl + avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_follower_avatar);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class FollowerViewHolder extends RecyclerView.ViewHolder {
        private FollowerPresenter followerPresenter;
        private List<User> users;

        @BindView(R.id.ll_follower)
        LinearLayout ll_follower;

        @BindView(R.id.iv_follower_avatar)
        ImageView iv_follower_avatar;

        @BindView(R.id.tv_follower_name)
        TextView tv_follower_name;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        public FollowerViewHolder(FollowerPresenter followerPresenter, List<User> users, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.followerPresenter = followerPresenter;
            this.users = users;
        }

        @OnClick(R.id.ll_follower)
        public void onClickFollower(){
            int position = getAdapterPosition();
            User user = users.get(position);
            followerPresenter.onClickFollower(user);
        }
    }
}
