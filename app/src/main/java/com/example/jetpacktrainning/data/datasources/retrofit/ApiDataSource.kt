package com.example.jetpacktrainning.data.datasources.retrofit

import com.example.jetpacktrainning.data.datasources.retrofit.model.DataListResponse
import com.example.jetpacktrainning.data.datasources.retrofit.model.DataResponse
import com.example.jetpacktrainning.tools.Constant.API_KEY_VALUE
import com.example.jetpacktrainning.tools.Constant.COUNTRIES
import com.example.jetpacktrainning.tools.Constant.KEY_API_KEY
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDataSource {
    @GET("$COUNTRIES?$KEY_API_KEY=$API_KEY_VALUE")
    suspend fun getAllCountries(): DataListResponse

    @GET("$COUNTRIES/{id}?$KEY_API_KEY=$API_KEY_VALUE")
    suspend fun getCountryById(@Path("id") id: Int): DataResponse
}