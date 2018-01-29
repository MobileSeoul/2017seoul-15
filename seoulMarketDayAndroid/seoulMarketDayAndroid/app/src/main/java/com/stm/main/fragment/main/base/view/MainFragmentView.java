package com.stm.main.fragment.main.base.view;

import android.view.View;

import com.stm.common.dao.Market;
import com.stm.common.dao.RegionCategory;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.LocationDto;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public interface MainFragmentView {

    void showMessage(String message);

    void showRecommendTitle(String message);

    void showRecommendLocationIcon();

    void showRecommendBestIcon();

    void showProgressDialog();

    void goneProgressDialog();

    void setMarketByLocationItem(List<Market> markets);

    void setMarketByFollowerItem(List<Market> markets);

    void navigateToStoryDetailActivity(Story story, int position);

    LocationDto getCurrentLocation();

    void setLocationManager();

    void setLocationListener();

    void clearMarketAdapter();

    void onClickMarketRefresh();

    void onClickRegion(View view);

    void navigateToRegionCategoryActivity(RegionCategory regionCategory);

    void navigateToMarketActivity(Market market);

    void setBest5UserItem(List<User> best5Users);

    void navigateToMerchantDetailActivity(User user, int position);

    void setBest5StoryItem(List<Story> best5Stories);

    void navigateToSearchTagActivity(String tagName);
}
