package com.nimble.nimblesurveys.data.remote.service

import com.nimble.nimblesurveys.model.TokenResponse
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {

    @POST("oauth/token")
    suspend fun loginToken(@Body loginRequest: LoginRequest): Response<TokenResponse>

    @POST("oauth/token")
    suspend fun refreshToken(@Body refreshRequest: RefreshRequest): Response<TokenResponse>
}