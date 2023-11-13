package com.nimble.nimblesurveys.data.repository

import com.nimble.nimblesurveys.data.remote.datasource.SurveyRemoteDataSource
import com.nimble.nimblesurveys.model.SurveyData
import com.nimble.nimblesurveys.utils.Resource
import javax.inject.Inject

class SurveyRepository @Inject constructor(
    private val surveyRemoteDataSource: SurveyRemoteDataSource
) {
    suspend fun fetchSurveys(): Resource<List<SurveyData>?> {
        return surveyRemoteDataSource.fetchSurveys()
    }
}