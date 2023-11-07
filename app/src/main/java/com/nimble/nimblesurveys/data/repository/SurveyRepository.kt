package com.nimble.nimblesurveys.data.repository

import com.nimble.nimblesurveys.data.remote.datasource.SurveyRemoteDataSource
import com.nimble.nimblesurveys.model.survey.SurveyResponse
import javax.inject.Inject

class SurveyRepository @Inject constructor(
    private val surveyRemoteDataSource: SurveyRemoteDataSource
) {
    suspend fun fetchSurveys(): SurveyResponse {
        return surveyRemoteDataSource.fetchSurveys()
    }
}