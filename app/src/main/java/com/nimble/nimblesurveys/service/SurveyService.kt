package com.nimble.nimblesurveys.service

import com.nimble.nimblesurveys.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SurveyService {
    @GET("surveys")
    suspend fun fetchSurveys(
            @Query("page[number]") pageNumber: Int,
            @Query("page[size]") pageSize: Int
    ) : Response
}