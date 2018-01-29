package com.stm.main.fragment.notification.interactor.impl;

import com.stm.common.dao.FirebaseNotification;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.main.fragment.notification.interactor.NotificationInteractor;
import com.stm.main.fragment.notification.presenter.NotificationPresenter;
import com.stm.repository.remote.FirebaseNotificationRepository;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-08-29.
 */

public class NotificationInteractorImpl implements NotificationInteractor {
    private NotificationPresenter notificationPresenter;
    private User user;
    private UserRepository userRepository;
    private List<FirebaseNotification> firebaseNotifications;

    private FirebaseNotificationRepository firebaseNotificationRepository;

    private static final Logger logger = LoggerFactory.getLogger(NotificationInteractorImpl.class);


    public NotificationInteractorImpl(NotificationPresenter notificationPresenter) {
        this.notificationPresenter = notificationPresenter;
        this.firebaseNotifications = new ArrayList<>();
    }

    @Override
    public void setUserRepository(String accessToken) {
        userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setFirebaseNotificationRepository(String accessToken) {
        firebaseNotificationRepository = new NetworkInterceptor(accessToken).getRetrofitForFirebaseNotificationRepository().create(FirebaseNotificationRepository.class);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void getFirebaseNotificationListByUserIdAndOffset(long userId, long offset) {
        Call<List<FirebaseNotification>> callGetFirebaseNotificationListByUserIdAndOffsetApi = userRepository.findFirebaseNotificationListById(userId, offset);
        callGetFirebaseNotificationListByUserIdAndOffsetApi.enqueue(new Callback<List<FirebaseNotification>>() {
            @Override
            public void onResponse(Call<List<FirebaseNotification>> call, Response<List<FirebaseNotification>> response) {
                if (response.isSuccessful()) {
                    List<FirebaseNotification> firebaseNotifications = response.body();
                    notificationPresenter.onSuccessGetFirebaseNotificationListByUserIdAndOffset(firebaseNotifications);
                } else {
                    notificationPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<FirebaseNotification>> call, Throwable t) {
                log(t);
                notificationPresenter.onNetworkError(null);
            }
        });
    }

    private static void log(Throwable throwable) {
        StackTraceElement[] ste = throwable.getStackTrace();
        String className = ste[0].getClassName();
        String methodName = ste[0].getMethodName();
        int lineNumber = ste[0].getLineNumber();
        String fileName = ste[0].getFileName();

        if (LogFlag.printFlag) {
            if (logger.isInfoEnabled()) {
                logger.error("Exception: " + throwable.getMessage());
                logger.error(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
            }
        }
    }


    @Override
    public List<FirebaseNotification> getFirebaseNotifications() {
        return firebaseNotifications;
    }

    @Override
    public void setFirebaseNotifications(List<FirebaseNotification> firebaseNotifications) {
        this.firebaseNotifications = firebaseNotifications;
    }

    @Override
    public void setFirebaseNotificationsAddAll(List<FirebaseNotification> firebaseNotifications) {
        this.firebaseNotifications.addAll(firebaseNotifications);
    }

    @Override
    public boolean isRemoveAllByRemovingFirebaseNotifications(List<FirebaseNotification> firebaseNotifications){
        return this.firebaseNotifications.removeAll(firebaseNotifications);
    }

    @Override
    public void updateFirebaseNotification(final FirebaseNotification firebaseNotification, final int position) {
        long firebaseNotificationId = firebaseNotification.getId();

        Call<ResponseBody> callUpdateFirebaseNotificationApi = firebaseNotificationRepository.updateFirebaseNotification(firebaseNotificationId,firebaseNotification);
        callUpdateFirebaseNotificationApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    notificationPresenter.onSuccessUpdateFirebaseNotification(firebaseNotification, position);
                } else {
                    notificationPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                notificationPresenter.onNetworkError(null);
            }
        });
    }
}
