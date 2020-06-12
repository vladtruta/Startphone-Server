package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.MissingTutorial

data class MissingTutorialRequest(
    @SerializedName("packageName")
    val packageName: String? = null
) {
    fun toMissingTutorial(id: String): MissingTutorial? {
        packageName ?: return null

        return MissingTutorial(id, packageName)
    }
}