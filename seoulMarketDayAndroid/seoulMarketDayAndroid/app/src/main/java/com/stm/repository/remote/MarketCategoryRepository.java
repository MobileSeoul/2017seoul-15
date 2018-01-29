package com.stm.repository.remote;

import com.stm.common.dao.MarketCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 시장 카테고리 관련 레파지토리 레이어 인터페이스
 * <p>
 * 시장 카테고리 관련 api url 함수 정보
 */

public interface MarketCategoryRepository {
    /**
     * GET 요청메소드
     */

    @GET("all.json")
    Call<List<MarketCategory>> findMarketCategoryList();

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
