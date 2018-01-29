package com.stm.dialog.notification.presenter;

/**
 * Created by Dev-0 on 2017-08-30.
 */

public interface NotificationDialogPresenter {
    void onBackPressed();

    void init(String notificationMessage);

    void onClickConfirm();
}
