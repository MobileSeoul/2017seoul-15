package com.stm.main.fragment.notification.presenter.impl;

import com.stm.common.dao.FirebaseNotification;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.common.flag.NotificationFlag;
import com.stm.main.fragment.notification.interactor.NotificationInteractor;
import com.stm.main.fragment.notification.interactor.impl.NotificationInteractorImpl;
import com.stm.main.fragment.notification.presenter.NotificationPresenter;
import com.stm.main.fragment.notification.view.NotificationView;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-29.
 */

public class NotificationPresenterImpl implements NotificationPresenter {
    private NotificationView notificationView;
    private NotificationInteractor notificationInteractor;

    public NotificationPresenterImpl(NotificationView notificationView) {
        this.notificationView = notificationView;
        this.notificationInteractor = new NotificationInteractorImpl(this);

    }

    @Override
    public void init(User user) {
        notificationInteractor.setUser(user);

        if (user != null) {
            String accessToken = user.getAccessToken();
            notificationInteractor.setUserRepository(accessToken);
            notificationInteractor.setFirebaseNotificationRepository(accessToken);
        }

    }

    @Override
    public void onCreateView() {
        User user = notificationInteractor.getUser();
        if (user != null) {
            notificationView.showProgressDialog();
            long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
            long userId = user.getId();
            notificationInteractor.getFirebaseNotificationListByUserIdAndOffset(userId, offset);
        } else {
            notificationView.goneNotificationView();
            notificationView.showEmptyView();
            notificationView.showEmptyViewMessage("로그인이 필요한 서비스입니다.");
            notificationView.showLoginPageButton();
        }

        notificationView.setOnRefreshListener();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            notificationView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            notificationView.showMessage(httpErrorDto.message());
        }
    }

    @Override
    public void onSuccessGetFirebaseNotificationListByUserIdAndOffset(List<FirebaseNotification> newFirebaseNotifications) {
        int newFirebaseNotificationSize = newFirebaseNotifications.size();

        List<FirebaseNotification> firebaseNotifications = notificationInteractor.getFirebaseNotifications();
        int firebaseNotificationSize = firebaseNotifications.size();

        if (firebaseNotificationSize == 0) {
            if (newFirebaseNotificationSize > 0) {
                notificationView.setOnScrollChangeListener();
                notificationInteractor.setFirebaseNotifications(newFirebaseNotifications);
                notificationView.setFirebaseNotificationsItem(newFirebaseNotifications);
                notificationView.setSwipeRefreshLayoutRefreshing(false);
            } else {
                notificationView.goneNotificationView();
                notificationView.showEmptyView();
                notificationView.showEmptyViewMessage("알림이 없습니다.");
                notificationView.goneLoginPageButton();
            }
        } else {
            notificationInteractor.setFirebaseNotificationsAddAll(newFirebaseNotifications);
            notificationView.notificationAdapterNotifyItemRangeInserted(firebaseNotificationSize, newFirebaseNotificationSize);
        }

        notificationView.goneProgressDialog();
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            notificationView.showProgressDialog();
            User user = notificationInteractor.getUser();
            List<FirebaseNotification> firebaseNotifications = notificationInteractor.getFirebaseNotifications();
            int firebaseNotificationSize = firebaseNotifications.size();

            long offset = firebaseNotificationSize;
            long userId = user.getId();
            notificationInteractor.getFirebaseNotificationListByUserIdAndOffset(userId, offset);
        }
    }

    @Override
    public void onClickNotification(FirebaseNotification firebaseNotification, int position) {
        int checked = firebaseNotification.getChecked();

        if (checked == NotificationFlag.NOT_CHECKED) {
            firebaseNotification.setChecked(NotificationFlag.CHECKED);
            notificationInteractor.updateFirebaseNotification(firebaseNotification, position);
        } else {
            long navigationCategoryId = firebaseNotification.getNavigationCategoryId();
            if (navigationCategoryId == NotificationFlag.NAVIGATION_TO_STORY) {
                notificationView.navigateToStoryDetailActivity(firebaseNotification, position);
            }

            if (navigationCategoryId == NotificationFlag.NAVIGATION_TO_USER) {
                notificationView.navigateToUserDetailActivity(firebaseNotification, position);
            }
        }
    }

    @Override
    public void onSuccessUpdateFirebaseNotification(FirebaseNotification firebaseNotification, int position) {
        notificationInteractor.getFirebaseNotifications().get(position).setChecked(NotificationFlag.CHECKED);
        notificationView.notificationAdapterNotifyItemChanged(position);

        long navigationCategoryId = firebaseNotification.getNavigationCategoryId();
        if (navigationCategoryId == NotificationFlag.NAVIGATION_TO_STORY) {
            notificationView.navigateToStoryDetailActivity(firebaseNotification, position);
        }

        if (navigationCategoryId == NotificationFlag.NAVIGATION_TO_USER) {
            notificationView.navigateToUserDetailActivity(firebaseNotification, position);
        }
    }

    @Override
    public void onRefresh() {
        User user = notificationInteractor.getUser();
        if (user != null) {
            List<FirebaseNotification> firebaseNotifications = notificationInteractor.getFirebaseNotifications();
            int firebaseNotificationSize = firebaseNotifications.size();
            if (firebaseNotificationSize > 0) {
                boolean isRemoveAll = notificationInteractor.isRemoveAllByRemovingFirebaseNotifications(firebaseNotifications);

                if (isRemoveAll) {

                    notificationView.showProgressDialog();
                    long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
                    long userId = user.getId();
                    notificationInteractor.getFirebaseNotificationListByUserIdAndOffset(userId, offset);
                }
            }else{
                notificationView.setSwipeRefreshLayoutRefreshing(false);
            }
        } else {
            notificationView.setSwipeRefreshLayoutRefreshing(false);
        }
    }


}
