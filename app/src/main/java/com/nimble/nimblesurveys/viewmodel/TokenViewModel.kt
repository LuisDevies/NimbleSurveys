package com.nimble.nimblesurveys.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.nimblesurveys.data.repository.TokenRepository
import com.nimble.nimblesurveys.model.TokenResponse
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.Token
import com.nimble.nimblesurveys.data.remote.service.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val repository: TokenRepository
) : ViewModel() {

    private val _loginResponse = MutableLiveData<TokenResponse>()
    val loginResponse: LiveData<TokenResponse> = _loginResponse

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            try {
                val response = repository.loginToken(loginRequest)
                _loginResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveSessionToken(context: Context, token: Token) {
        val sessionManager = SessionManager(context)
        sessionManager.saveAuthToken(token)
    }

}