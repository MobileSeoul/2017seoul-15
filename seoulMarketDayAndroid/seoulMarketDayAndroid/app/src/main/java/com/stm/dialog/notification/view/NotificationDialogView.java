package com.stm.dialog.notification.view;

/**
 * Created by Dev-0 on 2017-08-30.
 */

public interface NotificationDialogView {
    void showNotificationMessage(String message);

    void onClickConfirm();

    void removeUser();

    void navigateToMainActivity();
}
