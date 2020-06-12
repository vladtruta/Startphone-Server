package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UsersTutorialsWatchedEntity: Table() {
    val userId = varchar("userId", 320).references(UserEntity.id)
    val tutorialId = integer("tutorialId").references(TutorialEntity.id)
    val watchCount = integer("watchCount")
    val rating = double("rating")
}