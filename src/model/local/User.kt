package com.vladtruta.model.local

import org.joda.time.DateTime

data class User(
    val id: String,
    val dateOfBirth: DateTime,
    val gender: Char
)