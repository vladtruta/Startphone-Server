package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UsersTutorialMissingRequestsEntity: Table() {
    val userId = varchar("userId", 100).references(UserEntity.id)
    val packageName = varchar("packageName", 50).references(ApplicationEntity.packageName)
    val requestCount = integer("requestCount")
}