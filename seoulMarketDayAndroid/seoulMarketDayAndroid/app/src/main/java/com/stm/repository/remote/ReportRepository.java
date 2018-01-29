package com.stm.repository.remote;

import com.stm.common.dao.Report;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Dev-0 on 2017-08-17.
 */

public interface ReportRepository {
    /**
     * GET 요청메소드
     */

    /**
     * POST 요청메소드
     */

    @POST("./")
    Call<ResponseBody> saveReport(@Body Report report);

    /**
     * PUT 요청메소드
     */

    /**
     * DELETE 요청메소드
     */
}
