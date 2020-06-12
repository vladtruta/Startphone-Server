package com.vladtruta.model.responses

import com.google.gson.annotations.SerializedName

data class TutorialResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("packageName")
    val packageName: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("videoUrl")
    val videoUrl: String
)