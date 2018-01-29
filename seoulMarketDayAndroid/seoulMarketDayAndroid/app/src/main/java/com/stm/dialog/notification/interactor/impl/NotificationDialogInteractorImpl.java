package com.stm.dialog.notification.interactor.impl;

import com.stm.dialog.notification.interactor.NotificationDialogInteractor;
import com.stm.dialog.notification.presenter.NotificationDialogPresenter;

/**
 * Created by Dev-0 on 2017-08-30.
 */

public class NotificationDialogInteractorImpl implements NotificationDialogInteractor {

    private NotificationDialogPresenter notificationDialogPresenter;

    public NotificationDialogInteractorImpl(NotificationDialogPresenter notificationDialogPresenter) {
        this.notificationDialogPresenter = notificationDialogPresenter;
    }
}
