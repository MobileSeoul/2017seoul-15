package com.stm.user.edit.profile.interactor;

import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;

/**
 * Created by Dev-0 on 2017-08-22.
 */

public interface UserProfileEditInteractor {
    void setUserRepository(String accessToken);

    User getUser();

    void setUser(User user);

    void updateUser(User user, FileDto fileDto);

    void updateUser(User user);

    boolean isFirstChecked();

    void setFirstChecked(boolean firstChecked);
}
