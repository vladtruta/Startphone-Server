package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.Application

data class ApplicationListRequest(
    @SerializedName("applications")
    val applications: List<ApplicationRequest>? = null
) {
    data class ApplicationRequest(
        @SerializedName("packageName")
        val packageName: String? = null,
        @SerializedName("name")
        val name: String? = null
    ) {
        fun toApplication(): Application? {
            packageName ?: return null
            name ?: return null

            return Application(packageName, name)
        }
    }
}