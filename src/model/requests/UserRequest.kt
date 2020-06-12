package com.vladtruta.model.requests

import com.google.gson.annotations.SerializedName
import com.vladtruta.model.local.User
import org.joda.time.DateTime

data class UserRequest(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("dateOfBirth")
    val dateOfBirth: DateTime? = null,
    @SerializedName("gender")
    val gender: Char? = null
) {
    fun toUser(): User? {
        id ?: return null
        dateOfBirth ?: return null
        gender ?: return null

        return User(id, dateOfBirth, gender)
    }
}