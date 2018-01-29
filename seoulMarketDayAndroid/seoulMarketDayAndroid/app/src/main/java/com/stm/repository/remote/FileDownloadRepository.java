package com.stm.repository.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public interface FileDownloadRepository {
    @GET("{url}")
    Call<ResponseBody> downloadFileByUrl(@Path("url") String url);
}
