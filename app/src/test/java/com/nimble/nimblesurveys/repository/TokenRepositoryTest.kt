package com.nimble.nimblesurveys.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nimble.nimblesurveys.data.repository.TokenRepository
import com.nimble.nimblesurveys.model.TokenResponse
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import com.nimble.nimblesurveys.model.user.Token
import com.nimble.nimblesurveys.model.user.TokenData
import com.nimble.nimblesurveys.data.remote.datasource.TokenRemoteDataSource
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
class TokenRepositoryTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tokenRemoteDataSource: TokenRemoteDataSource

    private lateinit var repository: TokenRepository

    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        repository = TokenRepository(tokenRemoteDataSource)
    }

    @Test
    fun login_shouldReturnLoginResponse() = runTest {

        val mockResponse = Resource.success(
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

        val loginRequest = LoginRequest(
            "password",
            "email@email.com",
            "password",
            "id",
            "secret"
        )

        doReturn(mockResponse).`when`(tokenRemoteDataSource).loginToken(loginRequest)

        val value = repository.loginToken(loginRequest)
        Assert.assertEquals(mockResponse, value)
    }

    @Test
    fun refresh_shouldReturnRefreshResponse() = runTest {

        val mockResponse = Resource.success(
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

        val refreshRequest = RefreshRequest(
            "refresh_token",
            "mock_refresh",
            "mockID",
            "mockSecret"
        )

        doReturn(mockResponse).`when`(tokenRemoteDataSource).refreshToken(refreshRequest)

        val value = repository.refreshToken(refreshRequest)
        Assert.assertEquals(mockResponse, value)
    }
}
