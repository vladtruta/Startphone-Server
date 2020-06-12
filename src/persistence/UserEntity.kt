package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UserEntity: Table() {
    val idToken = text("idToken").primaryKey()
    val dateOfBirth = date("dateOfBirth")
    val gender = char("gender")
}