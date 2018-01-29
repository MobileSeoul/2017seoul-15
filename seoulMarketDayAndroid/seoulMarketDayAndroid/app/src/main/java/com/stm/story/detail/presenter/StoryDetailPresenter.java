package com.stm.story.detail.presenter;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-08-28.
 */

public interface StoryDetailPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void init(User user, long id);

    void onResume(User user);

    void onSuccessGetStoryById(Story story);

    void onClickComment();

    void onClickClose();

    void onClickMenu();

    void onClickPhoto(File file);

    void onClickPlayerButton(File file);

    void onClickAvatar();

    void onLikeChecked(boolean checked);

    void onHashTagClicked(String hashTag);

    void onActivityResultForCommentResultOk( int commentCountAdded);

    void onActivityResultForStoryEditResultOk(Story story);

    void onActivityResultForStoryResultDelete();

    void onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId();

    void onSuccessDeleteStoryLikeByIdAndStoryUserIdAndUserId();


}
