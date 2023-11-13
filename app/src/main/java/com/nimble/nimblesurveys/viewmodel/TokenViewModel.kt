package com.nimble.nimblesurveys.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.nimblesurveys.BuildConfig
import com.nimble.nimblesurveys.data.repository.TokenRepository
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.Token
import com.nimble.nimblesurveys.data.remote.service.SessionManager
import com.nimble.nimblesurveys.model.user.RefreshRequest
import com.nimble.nimblesurveys.model.user.TokenData
import com.nimble.nimblesurveys.utils.Constants
import com.nimble.nimblesurveys.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val repository: TokenRepository
) : ViewModel() {

    private val _loginResponse = MutableLiveData<Resource<TokenData?>>()
    val loginResponse: LiveData<Resource<TokenData?>> = _loginResponse
    private val _refreshResponse = MutableLiveData<Resource<TokenData?>>()
    val refreshResponse: LiveData<Resource<TokenData?>> = _refreshResponse

    fun login(context: Context, loginRequest: LoginRequest) {
        viewModelScope.launch {
            try {
                _loginResponse.value = Resource.loading()
                val response = repository.loginToken(loginRequest)
                _loginResponse.value = response
            } catch (e: Exception) {
                _loginResponse.value = Resource.error(e.message.toString())
            }
        }
    }

    fun refresh(refreshRequest: RefreshRequest) {
        viewModelScope.launch {
            try {
                _refreshResponse.value = Resource.loading()
                val response = repository.refreshToken(refreshRequest)
                _refreshResponse.value = response
            } catch (e: Exception) {
                _refreshResponse.value = Resource.error(e.message.toString())
            }
        }
    }

    fun createRefreshRequest(sessionManager: SessionManager): RefreshRequest {
        val token = sessionManager.fetchAuthToken()
        return RefreshRequest(
            Constants.refreshToken,
            token?.refreshToken ?: "",
            BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRET
        )
    }

    fun saveSessionToken(sessionManager: SessionManager, token: Token) {
        sessionManager.saveAuthToken(token)
    }

    fun isLogged(context: Context): Boolean {
        val sessionManager = SessionManager(context)
        return (sessionManager.fetchAuthToken() != null)
    }

    fun isTokenExpired(context: Context): Boolean {
        val sessionManager = SessionManager(context)
        return sessionManager.isAccessTokenExpired()
    }

}