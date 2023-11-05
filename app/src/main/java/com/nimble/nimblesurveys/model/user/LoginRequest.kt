package com.nimble.nimblesurveys.model.user

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("client_id")
    val clientid: String,
    @SerializedName("client_secret")
    val clientSecret: String
)
