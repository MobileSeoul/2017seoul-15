package com.stm.main.fragment.notification.adapter;

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
import com.stm.common.dao.Behavior;
import com.stm.common.dao.FirebaseNotification;
import com.stm.common.dao.User;
import com.stm.common.flag.NotificationFlag;
import com.stm.common.util.CalculateDateUtil;
import com.stm.main.fragment.notification.presenter.NotificationPresenter;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-08-29.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private NotificationPresenter notificationPresenter;
    private List<FirebaseNotification> firebaseNotifications;
    private Context context;
    private int layout;

    public NotificationAdapter(NotificationPresenter notificationPresenter, List<FirebaseNotification> firebaseNotifications, Context context, int layout) {
        this.notificationPresenter = notificationPresenter;
        this.firebaseNotifications = firebaseNotifications;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NotificationViewHolder notificationViewHolder = new NotificationViewHolder(notificationPresenter, firebaseNotifications, LayoutInflater.from(context).inflate(layout, parent, false));
        return notificationViewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        FirebaseNotification firebaseNotification = firebaseNotifications.get(position);
        User user = firebaseNotification.getUser();
        Behavior behavior = firebaseNotification.getBehavior();

        String avatar = user.getAvatar();
        String dateTime = firebaseNotification.getCreatedAt();
        String content = user.getName() + "님이 " + firebaseNotification.getContent() + behavior.getName();

        int checked = firebaseNotification.getChecked();

        holder.tv_notification_content.setText(content);
        holder.tv_notification_date.setText(CalculateDateUtil.getCalculateDateByDateTime(dateTime));
        Glide.with(context).load(holder.userAvatarUrl + avatar).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_notification_useravatar);

        if (checked == NotificationFlag.NOT_CHECKED) {
            holder.ll_notification_info.setBackgroundColor(holder.lightPink);
        } else {
            holder.ll_notification_info.setBackgroundColor(holder.white);
        }
    }

    @Override
    public int getItemCount() {
        return firebaseNotifications.size();
    }


    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private NotificationPresenter notificationPresenter;
        private List<FirebaseNotification> firebaseNotifications;


        @BindView(R.id.tv_notification_content)
        TextView tv_notification_content;

        @BindView(R.id.tv_notification_date)
        TextView tv_notification_date;

        @BindView(R.id.ll_notification_info)
        LinearLayout ll_notification_info;

        @BindView(R.id.iv_notification_useravatar)
        ImageView iv_notification_useravatar;


        @BindString(R.string.cloud_front_user_avatar)
        String userAvatarUrl;

        @BindColor(R.color.lightPink)
        int lightPink;

        @BindColor(R.color.white)
        int white;


        public NotificationViewHolder(NotificationPresenter notificationPresenter, List<FirebaseNotification> firebaseNotifications, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.notificationPresenter = notificationPresenter;
            this.firebaseNotifications = firebaseNotifications;
        }

        @OnClick(R.id.ll_notification_info)
        public void onClickNotification() {
            int position = getAdapterPosition();
            FirebaseNotification firebaseNotification = firebaseNotifications.get(position);
            notificationPresenter.onClickNotification(firebaseNotification, position);
        }

    }

}