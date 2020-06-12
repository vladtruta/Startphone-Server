package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.MissingTutorial

data class MissingTutorialRequest(
    @SerializedName("idToken")
    val idToken: String? = null,
    @SerializedName("packageName")
    val packageName: String? = null
) {
    fun toMissingTutorial(): MissingTutorial? {
        idToken ?: return null
        packageName ?: return null

        return MissingTutorial(idToken, packageName)
    }
}