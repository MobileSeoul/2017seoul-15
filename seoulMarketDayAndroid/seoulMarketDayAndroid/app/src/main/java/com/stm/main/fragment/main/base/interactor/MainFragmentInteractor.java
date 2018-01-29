package com.stm.main.fragment.main.base.interactor;

import com.stm.common.dto.LocationDto;

/**
 * Created by Dev-0 on 2017-06-14.
 */

public interface MainFragmentInteractor {

    LocationDto getLocationDto();

    void setLocationDto(LocationDto locationDto);

    void setMarketRepository();

    void setMarketRepository(String accessToken);

    void setUserRepository();

    void setUserRepository(String accessToken);

    void getMarketListByLocation(LocationDto locationDto);

    void getMarketListByFollower();

    void getBest5UserListPerMonth();

    void setStoryRepository(String accessToken);

    void setStoryRepository();

    void getBest5StoryListPerMonth();
}
