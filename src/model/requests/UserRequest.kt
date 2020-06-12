package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.User
import org.joda.time.DateTime

data class UserRequest(
    @SerializedName("idToken")
    val idToken: String? = null,
    @SerializedName("dateOfBirth")
    val dateOfBirth: DateTime? = null,
    @SerializedName("gender")
    val gender: Char? = null
) {
    fun toUser(): User? {
        idToken ?: return null
        dateOfBirth ?: return null
        gender ?: return null

        return User(idToken, dateOfBirth, gender)
    }
}