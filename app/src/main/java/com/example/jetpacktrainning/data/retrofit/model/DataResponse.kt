package com.example.jetpacktrainning.data.retrofit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("data")
    @Expose
    val data: CountryResponse,
    @SerializedName("query")
    val query: QueryResponse
)