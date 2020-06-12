package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UsersTutorialsWatchedEntity: Table() {
    val userEmail = varchar("userEmail", 320).references(UserEntity.email)
    val tutorialId = integer("tutorialId").references(TutorialEntity.id)
    val watchCount = integer("watchCount")
    val rating = double("rating")
}