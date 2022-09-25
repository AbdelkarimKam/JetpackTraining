package com.example.jetpacktrainning.data.datasources.retrofit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataListResponse(
    @SerializedName("data")
    @Expose
    val data: List<CountryResponse>,
    @SerializedName("query")
    val query: QueryResponse
)
