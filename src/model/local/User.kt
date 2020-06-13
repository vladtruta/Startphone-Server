package com.vladtruta.model.local

import org.joda.time.LocalDate

data class User(
    val id: String,
    val dateOfBirth: LocalDate,
    val gender: Char,
    val email: String
)