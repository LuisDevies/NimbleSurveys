package com.nimble.nimblesurveys.data.remote.datasource

import com.nimble.nimblesurveys.data.remote.service.SurveyService
import javax.inject.Inject

class SurveyRemoteDataSource @Inject constructor(
    private val surveyService: SurveyService
) {
    suspend fun fetchSurveys() = surveyService.fetchSurveys()
}
