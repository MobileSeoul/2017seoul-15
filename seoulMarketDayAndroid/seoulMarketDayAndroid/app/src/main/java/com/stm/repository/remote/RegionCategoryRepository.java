package com.stm.repository.remote;

import com.stm.common.dao.Market;
import com.stm.common.dao.RegionCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 지역구별 카테고리 관련 레파지토리 레이어 인터페이스
 * <p>
 * 지역구별 카테고리 관련 api url 함수 정보
 */

public interface RegionCategoryRepository {
    /**
     * GET 요청메소드
     */
    @GET("{id}.json")
    Call<RegionCategory> findRegionCategoryById(@Path("id") long id);

    @GET("{id}/markets.json")
    Call<List<Market>> findMarketListById(@Path("id") long id);

    @GET("{id}/markets.json")
    Call<List<Market>> findMarketListByIdAndMarketCategoryId(@Path("id") long id, @Query("marketCategoryId") long marketCategoryId);

    @GET("{id}/markets/nearness.json")
    Call<List<Market>> findMarketListByIdAndLocation(@Path("id") long id, @QueryMap HashMap<String, Double> map);


    /**
     * POST 요청메소드
     */

    /**
     * PUT 요청메소드
     */

    /**
     * DELETE 요청메소드
     */

}
