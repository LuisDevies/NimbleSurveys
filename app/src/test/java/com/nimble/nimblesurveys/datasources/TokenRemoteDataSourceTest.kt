package com.nimble.nimblesurveys.datasources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.doReturn
import com.nimble.nimblesurveys.model.TokenResponse
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import com.nimble.nimblesurveys.model.user.Token
import com.nimble.nimblesurveys.model.user.TokenData
import com.nimble.nimblesurveys.data.remote.datasource.TokenRemoteDataSource
import com.nimble.nimblesurveys.data.remote.service.TokenService
import com.nimble.nimblesurveys.model.Error
import com.nimble.nimblesurveys.model.ErrorResponse
import com.nimble.nimblesurveys.model.SurveyData
import com.nimble.nimblesurveys.model.survey.SurveyResponse
import com.nimble.nimblesurveys.utils.Resource
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class TokenRemoteDataSourceTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tokenService: TokenService

    private lateinit var tokenRemoteDataSource: TokenRemoteDataSource

    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        tokenRemoteDataSource = TokenRemoteDataSource(tokenService)
    }

    @Test
    fun login_shouldReturnLoginResponse() = runTest {

        val mockResponse = Response.success(
            TokenResponse(
                data = TokenData(
                    id = "mockId",
                    type = "mockType",
                    attributes = Token(
                        accessToken = "mockAccessToken",
                        refreshToken = "mockRefreshToken",
                        createdAt = 0,
                        tokenType = "mockType",
                        expiresIn = 0
                    )
                )
            )
        )

        val loginRequest = LoginRequest(
            "password",
            "email@email.com",
            "password",
            "id",
            "secret"
        )

        doReturn(mockResponse).`when`(tokenService).loginToken(loginRequest)

        val value = tokenRemoteDataSource.loginToken(loginRequest)
        Assert.assertEquals(Resource.success(mockResponse.body()!!.data), value)
        Assert.assertEquals(null, value.message)
    }

    @Test
    fun login_shouldReturnError() = runTest {

        val responseBody = ResponseBody.create(
            "application/json".toMediaTypeOrNull(), Gson().toJson(
                ErrorResponse(listOf<Error>((Error("ERROR", "400"))))
            )
        )

        val mockResponse: Response<ErrorResponse> = Response.error(
            400, responseBody
        )

        val loginRequest = LoginRequest(
            "password",
            "email@email.com",
            "password",
            "id",
            "secret"
        )

        doReturn(mockResponse).`when`(tokenService).loginToken(loginRequest)

        val value = tokenRemoteDataSource.loginToken(loginRequest)
        val expected: Resource<SurveyData?> = Resource.error(
            Gson().fromJson(
                mockResponse.errorBody()!!.charStream().readText(),
                ErrorResponse::class.java
            ).errors[0].detail
        )
        Assert.assertEquals(expected, value)
        Assert.assertEquals(null, value.data)
    }

    @Test
    fun refresh_shouldReturnRefreshResponse() = runTest {

        val mockResponse = Response.success(
            TokenResponse(
                data = TokenData(
                    id = "mockId",
                    type = "mockType",
                    attributes = Token(
                        accessToken = "mockAccessToken",
                        refreshToken = "mockRefreshToken",
                        createdAt = 0,
                        tokenType = "mockType",
                        expiresIn = 0
                    )
                )
            )
        )
        val refreshRequest = RefreshRequest(
            "refresh_token",
            "mock_refresh",
            "mockID",
            "mockSecret"
        )

        doReturn(mockResponse).`when`(tokenService).refreshToken(refreshRequest)

        val value = tokenRemoteDataSource.refreshToken(refreshRequest)
        Assert.assertEquals(Resource.success(mockResponse.body()!!.data), value)
    }

    @Test
    fun refresh_shouldReturnError() = runTest {

        val responseBody = ResponseBody.create(
            "application/json".toMediaTypeOrNull(), Gson().toJson(
                ErrorResponse(listOf<Error>((Error("ERROR", "400"))))
            )
        )

        val mockResponse: Response<ErrorResponse> = Response.error(
            400, responseBody
        )

        val refreshRequest = RefreshRequest(
            "refresh_token",
            "mock_refresh",
            "mockID",
            "mockSecret"
        )

        doReturn(mockResponse).`when`(tokenService).refreshToken(refreshRequest)

        val value = tokenRemoteDataSource.refreshToken(refreshRequest)
        val expected: Resource<SurveyData?> = Resource.error(
            Gson().fromJson(
                mockResponse.errorBody()!!.charStream().readText(),
                ErrorResponse::class.java
            ).errors[0].detail
        )
        Assert.assertEquals(expected, value)
        Assert.assertEquals(null, value.data)
    }
}
