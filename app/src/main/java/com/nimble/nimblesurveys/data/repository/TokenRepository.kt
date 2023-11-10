package com.nimble.nimblesurveys.data.repository

import com.nimble.nimblesurveys.data.remote.datasource.TokenRemoteDataSource
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import com.nimble.nimblesurveys.model.user.TokenData
import com.nimble.nimblesurveys.utils.Resource
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenRemoteDataSource: TokenRemoteDataSource
) {
    suspend fun refreshToken(refreshRequest: RefreshRequest): Resource<TokenData> {
        return tokenRemoteDataSource.refreshToken(refreshRequest)
    }

    suspend fun loginToken(loginRequest: LoginRequest): Resource<TokenData> {
        return tokenRemoteDataSource.loginToken(loginRequest)
    }
}