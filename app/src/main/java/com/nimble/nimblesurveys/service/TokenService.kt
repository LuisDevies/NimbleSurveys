package com.nimble.nimblesurveys.service

import com.nimble.nimblesurveys.model.Response
import com.nimble.nimblesurveys.model.user.LoginRequest
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenService {

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun refreshToken(@Body loginRequest: LoginRequest) : Response
}