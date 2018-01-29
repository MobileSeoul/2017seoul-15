package com.stm.main.fragment.main.search.fragment.user.presenter;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchUserPresenter {
    void onNetworkError(HttpErrorDto httpErrorDto);

    void onCreateView();

    void init(User user);

    void onClickUser(User user);

    void onScrollChange(int difference);

    void getUserListByKeyword(String newKeyword);

    void onSuccessGetUserListByKeywordAndOffset(List<User> users, String keyword);
}
