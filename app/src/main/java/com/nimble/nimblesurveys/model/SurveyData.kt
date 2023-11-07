package com.nimble.nimblesurveys.model

import com.nimble.nimblesurveys.model.survey.Survey

data class SurveyData(
    val id: String,
    val type: String,
    val attributes: Survey
)