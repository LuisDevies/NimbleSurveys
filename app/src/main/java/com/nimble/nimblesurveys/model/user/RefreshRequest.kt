package com.nimble.nimblesurveys.model.user

import com.google.gson.annotations.SerializedName

data class RefreshRequest(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String
)
