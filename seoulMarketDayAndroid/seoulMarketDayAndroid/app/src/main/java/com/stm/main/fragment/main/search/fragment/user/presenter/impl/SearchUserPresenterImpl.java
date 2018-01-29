package com.stm.main.fragment.main.search.fragment.user.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.common.flag.UserLevelFlag;
import com.stm.main.fragment.main.search.fragment.user.interactor.SearchUserInteractor;
import com.stm.main.fragment.main.search.fragment.user.interactor.impl.SearchUserInteractorImpl;
import com.stm.main.fragment.main.search.fragment.user.presenter.SearchUserPresenter;
import com.stm.main.fragment.main.search.fragment.user.view.SearchUserView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchUserPresenterImpl implements SearchUserPresenter {
    private SearchUserInteractor searchUserInteractor;
    private SearchUserView searchUserView;

    public SearchUserPresenterImpl(SearchUserView searchUserView) {
        this.searchUserInteractor = new SearchUserInteractorImpl(this);
        this.searchUserView = searchUserView;
    }

    @Override
    public void init(User user) {
        searchUserView.showProgressDialog();
        searchUserInteractor.setUser(user);

        if (user != null) {
            String accessToken = user.getAccessToken();
            searchUserInteractor.setUserRepository(accessToken);
        } else {
            searchUserInteractor.setUserRepository();
        }
        searchUserView.goneProgressDialog();
    }

    @Override
    public void onCreateView() {
        searchUserView.setOnScrollChangeListener();
    }

    @Override
    public void onClickUser(User user) {
        int level = user.getLevel();
        if(level == UserLevelFlag.NORMAL){
            searchUserView.navigateToUserDetailActivity(user);
        }

        if(level == UserLevelFlag.MERCHANT){
            searchUserView.navigateToMerchantDetailActivity(user);
        }
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 0) {
            searchUserView.showProgressDialog();

            String keyword = searchUserInteractor.getKeyword();
            User user = searchUserInteractor.getUser();

            if (user != null) {
                List<User> users = searchUserInteractor.getUsers();
                long offset = users.size();
                long userId = user.getId();

                searchUserInteractor.getUserListByKeywordAndOffset(keyword, userId, offset);
            } else {
                List<User> users = searchUserInteractor.getUsers();
                long offset = users.size();

                searchUserInteractor.getUserListByKeywordAndOffset(keyword, offset);
            }
        }
    }

    @Override
    public void getUserListByKeyword(String keyword) {
        searchUserView.showProgressDialog();

        String prevKeyword = searchUserInteractor.getKeyword();
        User user = searchUserInteractor.getUser();
        List<User> users = searchUserInteractor.getUsers();
        long offset= InfiniteScrollFlag.DEFAULT_OFFSET;

        if (prevKeyword != null && prevKeyword.equals(keyword)) {
            offset = users.size();
        }

        if (user != null) {
            long userId = user.getId();
            searchUserInteractor.getUserListByKeywordAndOffset(keyword, userId, offset);
        } else {
            searchUserInteractor.getUserListByKeywordAndOffset(keyword, offset);
        }
    }

    @Override
    public void onSuccessGetUserListByKeywordAndOffset(List<User> newUsers, String keyword) {
        String prevKeyword = searchUserInteractor.getKeyword();
        int newUserSize = newUsers.size();

        if (newUserSize > 0) {
            if (prevKeyword == null) {
                searchUserInteractor.setKeyword(keyword);
                searchUserInteractor.setUsers(newUsers);
                searchUserView.clearSearchUserAdapter();
                searchUserView.setSearchUserAdapterItem(newUsers);
                searchUserView.goneEmptyView();

            } else {
                if (prevKeyword.equals(keyword)) {
                    List<User> users = searchUserInteractor.getUsers();
                    int userSize = users.size();

                    searchUserInteractor.setUsersAddAll(newUsers);
                    searchUserView.notifyItemRangeInserted(userSize, newUserSize);
                    searchUserView.goneEmptyView();
                } else {
                    searchUserInteractor.setKeyword(keyword);
                    searchUserInteractor.setUsers(newUsers);
                    searchUserView.clearSearchUserAdapter();
                    searchUserView.setSearchUserAdapterItem(newUsers);
                    searchUserView.goneEmptyView();
                }
            }
        } else {
            if (prevKeyword == null) {
                searchUserView.showEmptyView();
            }
        }

        searchUserView.goneProgressDialog();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            searchUserView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            searchUserView.showMessage(httpErrorDto.message());
        }
    }
}
