package com.nimble.nimblesurveys.data.remote.service

import com.nimble.nimblesurveys.model.survey.SurveyResponse
import retrofit2.http.GET

interface SurveyService {
    @GET("surveys")
    suspend fun fetchSurveys(): SurveyResponse
}