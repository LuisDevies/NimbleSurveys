package com.nimble.nimblesurveys.data.remote.datasource

import com.nimble.nimblesurveys.data.remote.service.SurveyService
import com.nimble.nimblesurveys.model.SurveyData
import com.nimble.nimblesurveys.utils.Resource
import javax.inject.Inject

class SurveyRemoteDataSource @Inject constructor(
    private val surveyService: SurveyService
) {
    suspend fun fetchSurveys(): Resource<List<SurveyData>> {
        val response = surveyService.fetchSurveys()

        return if (response.data != null) {
            Resource.success(response.data)
        } else {
            Resource.error(
                response.errors?.get(0)?.code + " " + response.errors?.get(0)?.detail
            )
        }

    }
}
