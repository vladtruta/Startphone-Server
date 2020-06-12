package com.vladtruta.model.local

import org.joda.time.DateTime

data class User(
    val email: String,
    val dateOfBirth: DateTime,
    val gender: Char
)