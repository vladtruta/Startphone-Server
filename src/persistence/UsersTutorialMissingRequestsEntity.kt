package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UsersTutorialMissingRequestsEntity: Table() {
    val userIdToken = text("userIdToken").references(UserEntity.idToken)
    val packageName = text("packageName").references(ApplicationEntity.packageName)
    val requestCount = integer("requestCount")
}