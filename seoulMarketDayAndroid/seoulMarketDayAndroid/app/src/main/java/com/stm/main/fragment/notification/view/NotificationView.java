package com.stm.main.fragment.notification.view;

import com.stm.common.dao.FirebaseNotification;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-29.
 */

public interface NotificationView {
    void onClickLoginButton();

    void showProgressDialog();

    void goneProgressDialog();

    void setOnScrollChangeListener();

    void setOnRefreshListener();

    void showMessage(String message);

    void goneEmptyView();

    void showEmptyView();

    void showEmptyViewMessage(String message);

    void goneLoginPageButton();

    void showLoginPageButton();

    void goneNotificationView();

    void showNotificationView();

    void setFirebaseNotificationsItem(List<FirebaseNotification> firebaseNotifications);

    void notificationAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void navigateToStoryDetailActivity(FirebaseNotification firebaseNotification, int position);

    void navigateToUserDetailActivity(FirebaseNotification firebaseNotification, int position);

    void notificationAdapterNotifyItemChanged(int position);

    void setSwipeRefreshLayoutRefreshing(boolean refreshing);
}
