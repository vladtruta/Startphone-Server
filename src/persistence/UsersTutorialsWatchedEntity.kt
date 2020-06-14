package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UsersTutorialsWatchedEntity: Table() {
    val userId = varchar("userId", 100).references(UserEntity.id)
    val tutorialId = integer("tutorialId").references(TutorialEntity.id)
    val watchCount = integer("watchCount")
}