package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UsersTutorialsWatchedEntity: Table() {
    val userIdToken = text("userIdToken").references(UserEntity.idToken)
    val tutorialId = integer("tutorialId").references(TutorialEntity.id)
    val watchCount = integer("watchCount")
    val rating = double("rating")
}