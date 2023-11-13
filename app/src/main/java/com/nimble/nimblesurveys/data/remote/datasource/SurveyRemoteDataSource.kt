package com.nimble.nimblesurveys.data.remote.datasource

import com.google.gson.Gson
import com.nimble.nimblesurveys.data.remote.service.SurveyService
import com.nimble.nimblesurveys.model.ErrorResponse
import com.nimble.nimblesurveys.model.SurveyData
import com.nimble.nimblesurveys.utils.Resource
import javax.inject.Inject

class SurveyRemoteDataSource @Inject constructor(
    private val surveyService: SurveyService
) {
    suspend fun fetchSurveys(): Resource<List<SurveyData>?> {
        val response = surveyService.fetchSurveys()

        return if (response.isSuccessful) {
            Resource.success(response.body()?.data)
        } else {
            Resource.error(
                Gson().fromJson(response.errorBody()!!.charStream().readText(), ErrorResponse::class.java).errors[0].detail
            )
        }

    }
}
