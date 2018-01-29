package com.stm.login.create.joincategory.base.presenter;

import android.support.design.widget.TabLayout;

import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public interface JoinCategoryPresenter {
    void init(User user);

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onTabSelected(TabLayout.Tab tab);

    void onTabUnselected(TabLayout.Tab tab);

    void onResume(User user);
}
