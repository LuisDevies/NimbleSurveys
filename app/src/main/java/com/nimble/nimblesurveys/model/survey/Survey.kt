package com.nimble.nimblesurveys.model.survey

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Survey(
    val title: String,
    val description: String,
    @SerializedName("cover_image_url")
    val coverImage: String
) : Parcelable
