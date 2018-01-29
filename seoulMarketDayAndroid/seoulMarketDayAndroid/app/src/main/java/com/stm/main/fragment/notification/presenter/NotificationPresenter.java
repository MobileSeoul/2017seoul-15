package com.stm.main.fragment.notification.presenter;

import com.stm.common.dao.FirebaseNotification;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-29.
 */

public interface NotificationPresenter {
    void init(User user);

    void onCreateView();

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetFirebaseNotificationListByUserIdAndOffset(List<FirebaseNotification> firebaseNotifications);

    void onScrollChange(int difference);

    void onClickNotification(FirebaseNotification firebaseNotification, int position);

    void onSuccessUpdateFirebaseNotification(FirebaseNotification firebaseNotification, int position);

    void onRefresh();
}
