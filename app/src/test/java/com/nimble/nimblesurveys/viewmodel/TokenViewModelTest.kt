package com.nimble.nimblesurveys.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nimble.nimblesurveys.data.repository.TokenRepository
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.model.user.RefreshRequest
import com.nimble.nimblesurveys.model.user.Token
import com.nimble.nimblesurveys.model.user.TokenData
import com.nimble.nimblesurveys.utils.Resource
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
    private lateinit var observer: Observer<Resource<TokenData?>>

    @Mock
    private lateinit var context: Context


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

        doReturn(mockResponse).`when`(repository).loginToken(loginRequest)

        viewModel.login(context, loginRequest)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(mockResponse, viewModel.loginResponse.value)
        assertEquals(null, viewModel.loginResponse.value?.message)
    }

    @Test
    fun login_shouldReturnError() = runTest {

        val mockResponse: Resource<TokenData> = Resource.error(
            "ERROR"
        )

        val loginRequest = LoginRequest(
            "password",
            "email@email.com",
            "password",
            "id",
            "secret"
        )

        doReturn(mockResponse).`when`(repository).loginToken(loginRequest)

        viewModel.login(context, loginRequest)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(mockResponse, viewModel.loginResponse.value)
        assertEquals(null, viewModel.refreshResponse.value?.data)
    }

    @Test
    fun refresh_shouldUpdateRefreshResponse() = runTest {

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

        doReturn(mockResponse).`when`(repository).refreshToken(refreshRequest)

        viewModel.refresh(refreshRequest)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(mockResponse, viewModel.refreshResponse.value)
        assertEquals(null, viewModel.refreshResponse.value?.message)
    }

    @Test
    fun refresh_shouldReturnError() = runTest {

        val mockResponse: Resource<TokenData> = Resource.error(
            "ERROR"
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
        assertEquals(null, viewModel.refreshResponse.value?.data)
    }

}
