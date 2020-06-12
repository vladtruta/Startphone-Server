package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.User
import org.joda.time.DateTime

data class UserRequest(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("dateOfBirth")
    val dateOfBirth: DateTime? = null,
    @SerializedName("gender")
    val gender: Char? = null
) {
    fun toUser(): User? {
        email ?: return null
        dateOfBirth ?: return null
        gender ?: return null

        return User(email, dateOfBirth, gender)
    }
}