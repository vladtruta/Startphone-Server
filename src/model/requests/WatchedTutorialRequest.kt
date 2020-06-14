package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.WatchedTutorial

data class WatchedTutorialRequest(
    @SerializedName("tutorialId")
    val tutorialId: Int? = null
) {
    fun toWatchedTutorial(id: String): WatchedTutorial? {
        tutorialId ?: return null

        return WatchedTutorial(id, tutorialId)
    }
}