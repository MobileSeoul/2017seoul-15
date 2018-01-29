package com.stm.dialog.story.view;

import com.stm.common.dao.Story;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public interface StoryDialogView {
    void showMessage(String message);

    void setStoryDialogToOthersAdapterItem(ArrayList<String> messages);

    void setStoryDialogToAuthorAdapterItem(ArrayList<String> messages);

    void setContentCopied(String content);

    void navigateToBack();

    void showProgressDialog();

    void goneProgressDialog();

    void navigateToLoginActivity();

    void navigateToBackWithResultDelete(int position);

    void navigateToBackWithResultEdit(Story story, int position);

    void navigateToStoryEditActivity(Story story);
}
