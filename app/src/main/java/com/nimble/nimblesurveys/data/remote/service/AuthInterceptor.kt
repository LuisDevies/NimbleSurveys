package com.nimble.nimblesurveys.data.remote.service

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    context: Context
) : Interceptor {

    private val sessionManager = SessionManager(context)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "${it.tokenType} ${it.accessToken}")
        }

        return chain.proceed(requestBuilder.build())
    }
}