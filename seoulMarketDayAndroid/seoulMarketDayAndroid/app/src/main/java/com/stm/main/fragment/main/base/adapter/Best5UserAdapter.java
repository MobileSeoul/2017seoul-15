package com.stm.main.fragment.main.base.adapter;

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
import com.stm.main.fragment.main.base.presenter.MainFragmentPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-29.
 */

public class Best5UserAdapter extends RecyclerView.Adapter<Best5UserAdapter.Best5UserViewHolder> {
    private MainFragmentPresenter mainFragmentPresenter;
    private List<User> best5Users;
    private Context context;
    private int layout;

    public Best5UserAdapter(MainFragmentPresenter mainFragmentPresenter, List<User> best5Users, Context context, int layout) {
        this.mainFragmentPresenter = mainFragmentPresenter;
        this.best5Users = best5Users;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public Best5UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Best5UserViewHolder best5UserViewHolder = new Best5UserViewHolder(mainFragmentPresenter, best5Users, LayoutInflater.from(context).inflate(layout, parent, false));
        return best5UserViewHolder;
    }

    @Override
    public void onBindViewHolder(Best5UserViewHolder holder, int position) {
        User user = best5Users.get(position);

        String name = user.getName();
        String avatar = user.getAvatar();
        String cover = user.getCover();
        String intro = user.getIntro();
        Market market = user.getMarket();
        String marketName = market.getName();

        holder.tv_rank_name.setText(name);
        holder.tv_rank_market.setText(marketName);
        holder.tv_rank_intro.setText(intro);

        Glide.with(context).load(holder.userAvatarUrl + avatar).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_rank_avatar);
//        Glide.with(context).load(holder.userCoverUrl + cover).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_rank_cover);
    }

    @Override
    public int getItemCount() {
        return best5Users.size();
    }

    public static class Best5UserViewHolder extends RecyclerView.ViewHolder {
        private MainFragmentPresenter mainFragmentPresenter;
        private List<User> best5Users;

//        @BindView(R.id.iv_rank_cover)
//        ImageView iv_rank_cover;

        @BindView(R.id.iv_rank_avatar)
        ImageView iv_rank_avatar;

        @BindView(R.id.tv_rank_name)
        TextView tv_rank_name;

        @BindView(R.id.tv_rank_intro)
        TextView tv_rank_intro;

        @BindView(R.id.tv_rank_market)
        TextView tv_rank_market;


        @BindString(R.string.cloud_front_user_cover)
        String userCoverUrl;

        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        public Best5UserViewHolder(MainFragmentPresenter mainFragmentPresenter, List<User> best5Users, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mainFragmentPresenter = mainFragmentPresenter;
            this.best5Users = best5Users;
        }

        @OnClick(R.id.ll_rank_info)
        public void onClickUser() {
            int position = getAdapterPosition();
            User user = best5Users.get(position);
            mainFragmentPresenter.onClickBestUser(user, position);
        }
    }
}