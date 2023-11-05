package com.nimble.nimblesurveys.model.survey

import com.google.gson.annotations.SerializedName

data class Survey(
    val title: String,
    val description: String,
    @SerializedName("cover_image_url")
    val coverImage: String
)
