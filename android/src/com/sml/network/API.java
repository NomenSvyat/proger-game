package com.sml.network;

import com.sml.Credentials;
import com.sml.models.Models;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by tim on 28.05.2016.
 */
public interface API {

    @GET("/user")
    Call<Credentials.Token> login(@Header("Authorization") String credentials);

    @GET("/search/code")
    Call<Models.FirstModel> search(@Header("Authorization") String credentials,
                                   @Query("q") String q);

    @GET
    Call<Models.DownloadModel> fetchFile(@Header("Authorization") String credentials,
                                         @Url String url);

    @GET
    Call<ResponseBody> downloadFile(@Header("Authorization") String credentials,
                                    @Url String url);
}
