package com.nimble.nimblesurveys.model

data class Response(
    val data: Data,
    val errors: List<Error>
)
