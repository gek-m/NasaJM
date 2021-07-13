package com.example.nasajm.network

import com.google.gson.annotations.SerializedName

data class ApiMarsPhotoResponse(
    @SerializedName("photos")
    val photos: List<ApiMarsPicture>?
)
