package com.stm.main.fragment.main.search.base.presenter.impl;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.main.fragment.main.search.base.interactor.SearchInteractor;
import com.stm.main.fragment.main.search.base.interactor.impl.SearchInteractorImpl;
import com.stm.main.fragment.main.search.base.presenter.SearchPresenter;
import com.stm.main.fragment.main.search.base.view.SearchView;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchPresenterImpl implements SearchPresenter {
    private SearchInteractor searchInteractor;
    private SearchView searchView;

    public SearchPresenterImpl(SearchView searchView) {
        this.searchInteractor = new SearchInteractorImpl(this);
        this.searchView = searchView;
    }

    @Override
    public void init(User user) {
//        searchView.showProgressDialog();
        searchInteractor.setUser(user);
        searchView.setTabLayout();
        searchView.setSearchTabAdapter();
        searchView.setEditTextChangedListener();
        searchView.setOffscreenPageLimit(2);
    }

    @Override
    public void onResume(User user) {
        if (user != null && searchInteractor.getUser() == null) {
            searchInteractor.setUser(user);
            searchView.setEditText("");
            searchView.setTagFragmentRefresh();
        }
    }

    @Override
    public void onClickBack() {
        searchView.navigateToBack();
    }

    @Override
    public void onClickClear() {
        searchView.setEditText("");
    }

    @Override
    public void onTextChanged(String message) {
        int messageLength = message.length();

        if (messageLength > 0) {
            searchView.setUserFragment(message);
            searchView.setMarketFragment(message);
            searchView.setTagFragment(message);
        }

    }

    @Override
    public void onChangeFragment() {
        searchView.showProgressDialog();
        User user = searchInteractor.getUser();
        searchView.setSearchTabAdapterUser(user);
        searchView.setSearchTabAdapterNotifyDataSetChanged();
        searchView.goneProgressDialog();
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            searchView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            searchView.showMessage(httpErrorDto.message());
        }
    }

}
