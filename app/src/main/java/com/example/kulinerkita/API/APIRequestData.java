package com.example.kulinerkita.API;

import com.example.kulinerkita.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelResponse> ardRetrieve();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("asal") String asal,
            @Field("deskripsi_singkat") String deskripsi_singkat,
            @Field("deskripsi_lengkap") String deskripsi_lengkap,
            @Field("photo") String photo
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ModelResponse> ardDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("asal") String asal,
            @Field("deskripsi_singkat") String deskripsi_singkat,
            @Field("deskripsi_lengkap") String deskripsi_lengkap,
            @Field("photo") String photo
    );
}
