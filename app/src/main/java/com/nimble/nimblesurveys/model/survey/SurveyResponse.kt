package com.nimble.nimblesurveys.model.survey

import com.nimble.nimblesurveys.model.Error
import com.nimble.nimblesurveys.model.SurveyData

data class SurveyResponse(
    val data: List<SurveyData>?,
    val errors: List<Error>?
)