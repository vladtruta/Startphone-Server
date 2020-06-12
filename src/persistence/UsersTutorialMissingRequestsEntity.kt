package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UsersTutorialMissingRequestsEntity: Table() {
    val userEmail = varchar("userEmail", 320).references(UserEntity.email)
    val packageName = varchar("packageName", 50).references(ApplicationEntity.packageName)
    val requestCount = integer("requestCount")
}