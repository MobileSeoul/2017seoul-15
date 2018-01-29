package com.stm.repository.remote;

import com.stm.common.dao.OpinionCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public interface OpinionCategoryRepository {
    /**
     * GET 요청메소드
     */

    @GET("all.json")
    Call<List<OpinionCategory>> findOpinionCategoryList();

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
