package com.example.jetpacktrainning.data.datasources.retrofit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("continent")
    @Expose
    val continent: String,
    @SerializedName("country_code")
    @Expose
    val countryCode: String?,
    @SerializedName("country_id")
    @Expose
    val countryId: Int,
    @SerializedName("name")
    @Expose
    val name: String
)