package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.RatedTutorial

data class RatedTutorialRequest(
    @SerializedName("tutorialId")
    val tutorialId: Int? = null,
    @SerializedName("useful")
    val useful: Boolean? = null
) {
    fun toRatedTutorial(id: String): RatedTutorial? {
        tutorialId ?: return null
        useful ?: return null

        return RatedTutorial(id, tutorialId, useful)
    }
}