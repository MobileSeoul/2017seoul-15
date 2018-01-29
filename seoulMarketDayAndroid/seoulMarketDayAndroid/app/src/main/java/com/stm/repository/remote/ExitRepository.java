package com.stm.repository.remote;

import com.stm.common.dao.Exit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface ExitRepository {
    /**
     * GET 요청메소드
     */


    /**
     * POST 요청메소드
     */
    @POST("./")
    Call<Boolean> saveExit(@Body Exit exit);

    /**
     * PUT 요청메소드
     */

    /**
     * DELETE 요청메소드
     */
}
