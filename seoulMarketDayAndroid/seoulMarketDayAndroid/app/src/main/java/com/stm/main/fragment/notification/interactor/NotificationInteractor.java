package com.stm.main.fragment.notification.interactor;

import com.stm.common.dao.FirebaseNotification;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by Dev-0 on 2017-08-29.
 */

public interface NotificationInteractor {
    void setUserRepository(String accessToken);

    void setFirebaseNotificationRepository(String accessToken);

    User getUser();

    void setUser(User user);

    void getFirebaseNotificationListByUserIdAndOffset(long userId, long offset);

    List<FirebaseNotification> getFirebaseNotifications();

    void setFirebaseNotifications(List<FirebaseNotification> firebaseNotifications);

    void setFirebaseNotificationsAddAll(List<FirebaseNotification> firebaseNotifications);

    boolean isRemoveAllByRemovingFirebaseNotifications(List<FirebaseNotification> firebaseNotifications);

    void updateFirebaseNotification(FirebaseNotification firebaseNotification, int position);
}
