package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.MissingTutorial

data class MissingTutorialRequest(
    @SerializedName("packageName")
    val packageName: String? = null
) {
    fun toMissingTutorial(email: String): MissingTutorial? {
        packageName ?: return null

        return MissingTutorial(email, packageName)
    }
}