package com.nimble.nimblesurveys.data.remote.datasource

import com.google.gson.Gson
import com.nimble.nimblesurveys.data.remote.service.TokenService
import com.nimble.nimblesurveys.model.ErrorResponse
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import com.nimble.nimblesurveys.model.user.TokenData
import com.nimble.nimblesurveys.utils.Resource
import javax.inject.Inject

class TokenRemoteDataSource @Inject constructor(
    private val tokenService: TokenService
) {
    suspend fun refreshToken(refreshRequest: RefreshRequest): Resource<TokenData?> {
        val response = tokenService.refreshToken(refreshRequest)

        return if (response.isSuccessful) {
            Resource.success(response.body()?.data)
        } else {
            Resource.error(
                Gson().fromJson(response.errorBody()!!.charStream().readText(), ErrorResponse::class.java).errors[0].detail
            )
        }
    }

    suspend fun loginToken(loginRequest: LoginRequest): Resource<TokenData?> {
        val response = tokenService.loginToken(loginRequest)

        return if (response.isSuccessful) {
            Resource.success(response.body()?.data)
        } else {
            Resource.error(
                Gson().fromJson(response.errorBody()!!.charStream().readText(), ErrorResponse::class.java).errors[0].detail
            )
        }
    }
}