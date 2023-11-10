package com.nimble.nimblesurveys.datasources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nimble.nimblesurveys.model.TokenResponse
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import com.nimble.nimblesurveys.model.user.Token
import com.nimble.nimblesurveys.model.user.TokenData
import com.nimble.nimblesurveys.data.remote.datasource.TokenRemoteDataSource
import com.nimble.nimblesurveys.data.remote.service.TokenService
import com.nimble.nimblesurveys.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

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

        val mockResponse = TokenResponse(
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
            ),
            errors = null
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
        Assert.assertEquals(Resource.success(mockResponse.data), value)
        Assert.assertEquals(null, value.message)
    }

    @Test
    fun login_shouldReturnError() = runTest {

        val mockResponse = TokenResponse(
            data = null,
            errors = listOf(com.nimble.nimblesurveys.model.Error("1", "ERROR"))
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
        val expected: Resource<TokenData> = Resource.error(
            mockResponse.errors?.get(0)?.code + " " + mockResponse.errors?.get(
                0
            )?.detail
        )
        Assert.assertEquals(expected, value)
        Assert.assertEquals(null, value.data)
    }

    @Test
    fun refresh_shouldReturnRefreshResponse() = runTest {

        val mockResponse = TokenResponse(
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
            ),
            errors = null
        )

        val refreshRequest = RefreshRequest(
            "refresh_token",
            "mock_refresh",
            "mockID",
            "mockSecret"
        )

        doReturn(mockResponse).`when`(tokenService).refreshToken(refreshRequest)

        val value = tokenRemoteDataSource.refreshToken(refreshRequest)
        Assert.assertEquals(Resource.success(mockResponse.data), value)
    }

    @Test
    fun refresh_shouldReturnError() = runTest {

        val mockResponse = TokenResponse(
            data = null,
            errors = listOf(com.nimble.nimblesurveys.model.Error("1", "ERROR"))
        )

        val refreshRequest = RefreshRequest(
            "refresh_token",
            "mock_refresh",
            "mockID",
            "mockSecret"
        )

        doReturn(mockResponse).`when`(tokenService).refreshToken(refreshRequest)

        val value = tokenRemoteDataSource.refreshToken(refreshRequest)
        val expected: Resource<TokenData> = Resource.error(
            mockResponse.errors?.get(0)?.code + " " + mockResponse.errors?.get(
                0
            )?.detail
        )
        Assert.assertEquals(expected, value)
        Assert.assertEquals(null, value.data)
    }
}
