package com.nimble.nimblesurveys.data.remote.service

import android.content.Context
import android.content.SharedPreferences
import com.nimble.nimblesurveys.R
import com.nimble.nimblesurveys.model.user.Token

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val TOKEN_TYPE = "token_type"
        const val EXPIRES_IN = "expires_in"
        const val REFRESH_TOKEN = "refresh_token"
        const val CREATED_AT = "created_at"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: Token) {
        val editor = prefs.edit()
        editor.putString(ACCESS_TOKEN, token.accessToken)
        editor.putString(TOKEN_TYPE, token.tokenType)
        editor.putLong(EXPIRES_IN, token.expiresIn)
        editor.putString(REFRESH_TOKEN, token.refreshToken)
        editor.putLong(CREATED_AT, token.createdAt)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): Token? {
        return if (prefs.contains(ACCESS_TOKEN)) {
            Token(
                prefs.getString(ACCESS_TOKEN, null),
                prefs.getString(TOKEN_TYPE, null),
                prefs.getLong(EXPIRES_IN, 0),
                prefs.getString(REFRESH_TOKEN, null),
                prefs.getLong(CREATED_AT, 0)
            )
        } else {
            null
        }
    }

    // Method to check if the access token has expired
    fun isAccessTokenExpired(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        val token = fetchAuthToken()
        return (token?.expiresIn != null) && (currentTimeMillis >= ( (token.createdAt  + token.expiresIn) * 1000))
    }

    // Method to update the access token and its expiration time in the session
    fun updateAccessToken(token: Token) {
        saveAuthToken(token)
    }

}