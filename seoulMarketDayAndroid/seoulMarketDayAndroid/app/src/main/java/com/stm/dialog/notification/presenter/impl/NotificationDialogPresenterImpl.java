package com.stm.dialog.notification.presenter.impl;

import com.stm.dialog.notification.interactor.NotificationDialogInteractor;
import com.stm.dialog.notification.interactor.impl.NotificationDialogInteractorImpl;
import com.stm.dialog.notification.presenter.NotificationDialogPresenter;
import com.stm.dialog.notification.view.NotificationDialogView;

/**
 * Created by Dev-0 on 2017-08-30.
 */

public class NotificationDialogPresenterImpl implements NotificationDialogPresenter {
    private  NotificationDialogView notificationDialogView;
    private NotificationDialogInteractor notificationDialogInteractor;
    public NotificationDialogPresenterImpl(NotificationDialogView notificationDialogView) {
        this.notificationDialogView = notificationDialogView;
        this.notificationDialogInteractor = new NotificationDialogInteractorImpl(this);
    }

    @Override
    public void onBackPressed() {
        notificationDialogView.removeUser();
        notificationDialogView.navigateToMainActivity();
    }

    @Override
    public void init(String notificationMessage) {
        notificationDialogView.showNotificationMessage(notificationMessage);
    }

    @Override
    public void onClickConfirm() {
        notificationDialogView.removeUser();
        notificationDialogView.navigateToMainActivity();
    }
}
