package com.stm.repository.remote;

import com.stm.common.dao.ExitCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface ExitCategoryRepository {
    /**
     * GET 요청메소드
     */

    @GET("all.json")
    Call<List<ExitCategory>> findExitCategoryList();

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
