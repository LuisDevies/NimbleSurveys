package com.nimble.nimblesurveys.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.nhaarman.mockitokotlin2.doReturn
import com.nimble.nimblesurveys.data.remote.datasource.SurveyRemoteDataSource
import com.nimble.nimblesurveys.data.repository.SurveyRepository
import com.nimble.nimblesurveys.model.SurveyData
import com.nimble.nimblesurveys.model.survey.Survey
import com.nimble.nimblesurveys.model.survey.SurveyResponse
import com.nimble.nimblesurveys.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

@RunWith(MockitoJUnitRunner::class)
class SurveyRepositoryTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteSurveyDataSource: SurveyRemoteDataSource

    private lateinit var repository: SurveyRepository

    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        repository = SurveyRepository(remoteSurveyDataSource)
    }

    @Test
    fun fetchSurveys_shouldReturnSurveyResponse() = runTest {

        val mockResponse = Resource.success(
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
            )
        )

        doReturn(mockResponse).`when`(remoteSurveyDataSource).fetchSurveys()

        val value = repository.fetchSurveys()
        assertEquals(mockResponse, value)
        assertEquals(1, value.data?.size)
    }

    @Test
    fun fetchSurveys_shouldReturnError() = runTest {

        val mockResponse: Resource<SurveyData> = Resource.error(
            "ERROR"
        )

        doReturn(mockResponse).`when`(remoteSurveyDataSource).fetchSurveys()

        val value = repository.fetchSurveys()
        assertEquals(mockResponse, value)
        assertEquals(null,value.data)
    }
}
