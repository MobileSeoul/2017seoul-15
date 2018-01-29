package com.stm.repository.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ㅇㅇ on 2017-08-07.
 */

public interface FileRepository {
    /**
     * GET 요청메소드
     */

    /**
     * POST 요청메소드
     */

    /**
     * PUT 요청메소드
     */
    @PUT("{id}/hits")
    Call<ResponseBody> updateFileByHits(@Path("id") long id);

    /**
     * DELETE 요청메소드
     */
}
