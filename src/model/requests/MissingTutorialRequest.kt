package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.MissingTutorial

data class MissingTutorialRequest(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("packageName")
    val packageName: String? = null
) {
    fun toMissingTutorial(): MissingTutorial? {
        email ?: return null
        packageName ?: return null

        return MissingTutorial(email, packageName)
    }
}