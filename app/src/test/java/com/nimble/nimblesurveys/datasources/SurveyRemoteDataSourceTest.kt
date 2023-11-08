package com.nimble.nimblesurveys.datasources


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.nhaarman.mockitokotlin2.doReturn
import com.nimble.nimblesurveys.data.remote.datasource.SurveyRemoteDataSource
import com.nimble.nimblesurveys.data.remote.service.SurveyService
import com.nimble.nimblesurveys.model.SurveyData
import com.nimble.nimblesurveys.model.survey.Survey
import com.nimble.nimblesurveys.model.survey.SurveyResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

@RunWith(MockitoJUnitRunner::class)
class SurveyRemoteDataSourceTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var surveyService: SurveyService

    private lateinit var surveyRemoteDataSource: SurveyRemoteDataSource

    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        surveyRemoteDataSource = SurveyRemoteDataSource(surveyService)
    }

    @Test
    fun fetchSurveys_shouldReturnSurveyResponse() = runTest {

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

        doReturn(mockResponse).`when`(surveyService).fetchSurveys()

        val value = surveyRemoteDataSource.fetchSurveys()
        assertEquals(mockResponse, value)
        assertEquals(1, value.data?.size)
    }
}
