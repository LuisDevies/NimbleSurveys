package com.nimble.nimblesurveys.data.repository

import com.nimble.nimblesurveys.data.remote.datasource.TokenRemoteDataSource
import com.nimble.nimblesurveys.model.TokenResponse
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenRemoteDataSource: TokenRemoteDataSource
) {
    suspend fun refreshToken(refreshRequest: RefreshRequest): TokenResponse {
        return tokenRemoteDataSource.refreshToken(refreshRequest)
    }

    suspend fun loginToken(loginRequest: LoginRequest): TokenResponse {
        return tokenRemoteDataSource.loginToken(loginRequest)
    }
}