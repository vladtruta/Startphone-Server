package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UserEntity: Table() {
    val email = varchar("email", 320).primaryKey()
    val dateOfBirth = date("dateOfBirth")
    val gender = char("gender")
}