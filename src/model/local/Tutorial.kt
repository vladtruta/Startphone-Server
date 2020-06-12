package com.vladtruta.model.local

import com.vladtruta.model.responses.TutorialResponse

data class Tutorial(
    val id: Int,
    val packageName: String,
    val title: String,
    val videoUrl: String,
    val rating: Double
) {
    fun toTutorialResponse(): TutorialResponse {
        // Tutorial rating does not get sent to client
        return TutorialResponse(id, packageName, title, videoUrl)
    }
}