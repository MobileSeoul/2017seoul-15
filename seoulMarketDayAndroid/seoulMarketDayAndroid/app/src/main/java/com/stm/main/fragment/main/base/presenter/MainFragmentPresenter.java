package com.stm.main.fragment.main.base.presenter;

import com.stm.common.dao.Market;
import com.stm.common.dao.RegionCategory;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public interface MainFragmentPresenter {
    void init();

    void onCreateView();

    void onNetworkError(HttpErrorDto httpErrorDto);

    void onSuccessGetMarketListByLocation(List<Market> markets);

    void onSuccessGetMarketListByFollower(List<Market> markets);

    void onClickMarketRefresh();

    void onClickRegion(RegionCategory regionCategory);

    void onClickMarket(Market market);

    void onSuccessGetBest5UserListPerMonth(List<User> best5Users);

    void onClickBestUser(User user, int position);

    void onClickBestStory(Story story, int position);

    void onHashTagClicked(String hashTag);

    void onSuccessGetBest5StoryListPerMonth(List<Story> best5Stories);
}
