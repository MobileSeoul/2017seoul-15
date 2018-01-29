package com.stm.dialog.story.presenter;

import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public interface StoryDialogPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, Story story, int position);


    void onClickStoryDialogToOthersLayout(int position);

    void onClickStoryDialogToAuthorLayout(int position);

    void onSuccessSetReport();

    void onSuccessDeleteStory();

    void onActivityResultForStoryEditResultOk(Story story);
}
