package com.nimble.nimblesurveys.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nimble.nimblesurveys.data.repository.TokenRepository
import com.nimble.nimblesurveys.model.TokenResponse
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import com.nimble.nimblesurveys.model.user.Token
import com.nimble.nimblesurveys.model.user.TokenData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

@RunWith(MockitoJUnitRunner::class)
class TokenViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TokenRepository

    @Mock
    private lateinit var observer: Observer<TokenResponse>


    private lateinit var viewModel: TokenViewModel

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = TokenViewModel(repository)
        viewModel.loginResponse.observeForever(observer)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun login_shouldUpdateLoginResponse() = runTest {

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

        doReturn(mockResponse).`when`(repository).loginToken(loginRequest)

        viewModel.login(loginRequest)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(mockResponse, viewModel.loginResponse.value)
    }

    @Test
    fun refresh_shouldUpdateRefreshResponse() = runTest {

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

        doReturn(mockResponse).`when`(repository).refreshToken(refreshRequest)

        viewModel.refresh(refreshRequest)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(mockResponse, viewModel.refreshResponse.value)
    }

}
