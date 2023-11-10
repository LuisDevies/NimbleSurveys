package com.nimble.nimblesurveys.di

import android.content.Context
import com.nimble.nimblesurveys.data.remote.datasource.SurveyRemoteDataSource
import com.nimble.nimblesurveys.data.remote.datasource.TokenRemoteDataSource
import com.nimble.nimblesurveys.data.repository.TokenRepository
import com.nimble.nimblesurveys.data.remote.service.AuthInterceptor
import com.nimble.nimblesurveys.data.remote.service.SurveyService
import com.nimble.nimblesurveys.data.remote.service.TokenService
import com.nimble.nimblesurveys.data.repository.SurveyRepository
import com.nimble.nimblesurveys.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = Constants.apiUrl

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {

        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        logginInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logginInterceptor)
            .addInterceptor(AuthInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    @Provides
    fun provideTokenService(retrofit: Retrofit): TokenService =
        retrofit.create(TokenService::class.java)

    @Provides
    fun provideSurveyService(retrofit: Retrofit): SurveyService =
        retrofit.create(SurveyService::class.java)

    @Singleton
    @Provides
    fun provideSurveyRemoteDataSource(surveyService: SurveyService) =
        SurveyRemoteDataSource(surveyService)

    @Singleton
    @Provides
    fun provideTokenRemoteDataSource(tokenService: TokenService) =
        TokenRemoteDataSource(tokenService)


    @Singleton
    @Provides
    fun provideSurveyRepository(surveyRemoteDataSource: SurveyRemoteDataSource) =
        SurveyRepository(surveyRemoteDataSource)

    @Singleton
    @Provides
    fun provideTokenRepository(tokenRemoteDataSource: TokenRemoteDataSource) =
        TokenRepository(tokenRemoteDataSource)
}