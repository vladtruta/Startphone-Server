package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.WatchedTutorial

data class WatchedTutorialRequest(
    @SerializedName("idToken")
    val idToken: String? = null,
    @SerializedName("tutorialId")
    val tutorialId: Int? = null,
    @SerializedName("useful")
    val useful: Boolean? = null
) {
    fun toWatchedTutorial(): WatchedTutorial? {
        idToken ?: return null
        tutorialId ?: return null
        useful ?: return null

        return WatchedTutorial(idToken, tutorialId, useful)
    }
}