package com.nimble.nimblesurveys.data.remote.datasource

import com.nimble.nimblesurveys.data.remote.service.TokenService
import com.nimble.nimblesurveys.model.TokenResponse
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import javax.inject.Inject

class TokenRemoteDataSource @Inject constructor(
    private val tokenService: TokenService
) {
    suspend fun refreshToken(refreshRequest: RefreshRequest): TokenResponse {
        return tokenService.refreshToken(refreshRequest)
    }

    suspend fun loginToken(loginRequest: LoginRequest): TokenResponse {
        return tokenService.loginToken(loginRequest)
    }
}