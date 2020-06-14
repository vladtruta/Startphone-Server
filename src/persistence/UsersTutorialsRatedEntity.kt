package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object UsersTutorialsRatedEntity: Table() {
    val userId = varchar("userId", 320).references(UserEntity.id)
    val tutorialId = integer("tutorialId").references(TutorialEntity.id)
    val rating = double("rating")
}