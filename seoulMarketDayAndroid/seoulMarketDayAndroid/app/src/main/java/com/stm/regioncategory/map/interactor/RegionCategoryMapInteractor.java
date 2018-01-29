package com.stm.regioncategory.map.interactor;

import com.stm.common.dao.RegionCategory;
import com.stm.common.dto.LocationDto;

/**
 * Created by Dev-0 on 2017-06-19.
 */

public interface RegionCategoryMapInteractor {

    LocationDto getLocationDto();

    void setLocationDto(LocationDto locationDto);

    RegionCategory getRegionCategory();

    void setRegionCategory(RegionCategory regionCategory);

    void setRegionCategoryRepository();

    void getMarketListById(long regionCategoryId);

    void getMarketListByIdAndLocation(long regionCategoryId, LocationDto locationDto);
}
