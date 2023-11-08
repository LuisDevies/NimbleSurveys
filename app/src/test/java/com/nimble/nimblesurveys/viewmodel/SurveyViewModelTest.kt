package com.nimble.nimblesurveys.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nimble.nimblesurveys.data.repository.SurveyRepository
import com.nimble.nimblesurveys.model.SurveyData
import com.nimble.nimblesurveys.model.survey.Survey
import com.nimble.nimblesurveys.model.survey.SurveyResponse
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
class SurveyViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: SurveyRepository

    @Mock
    private lateinit var observer: Observer<SurveyResponse>


    private lateinit var viewModel: SurveyViewModel

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SurveyViewModel(repository)
        viewModel.surveyResponse.observeForever(observer)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchSurveys_shouldUpdateSurveyResponse() = runTest {

        val mockResponse = SurveyResponse(
            data = listOf(
                SurveyData(
                    id = "mockID",
                    type = "mockType",
                    attributes = Survey(
                        title = "mockTitle",
                        description = "mockDescription",
                        coverImage = "mockUrl"
                    )
                )
            ),
            errors = null
        )

        doReturn(mockResponse).`when`(repository).fetchSurveys()

        viewModel.fetchSurveys()
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(mockResponse, viewModel.surveyResponse.value)
        assertEquals(1, viewModel.surveyResponse.value?.data?.size)

    }
}
