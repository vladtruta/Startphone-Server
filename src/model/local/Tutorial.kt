package com.vladtruta.model.local

import com.vladtruta.model.responses.TutorialResponse

data class Tutorial(
    val packageName: String,
    val title: String,
    val videoUrl: String,
    val rating: Double,
    val id: Int = 0
) {
    fun toTutorialResponse(): TutorialResponse {
        // Tutorial rating does not get sent to client
        return TutorialResponse(id, packageName, title, videoUrl)
    }
}