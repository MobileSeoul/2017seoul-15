package com.stm.repository.remote;

import com.stm.common.dao.Opinion;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public interface OpinionRepository {
    /**
     * GET 요청메소드
     */


    /**
     * POST 요청메소드
     */
    @POST("./")
    Call<ResponseBody> saveOpinion(@Body Opinion opinion);

    /**
     * PUT 요청메소드
     */

    /**
     * DELETE 요청메소드
     */

}
