package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object TutorialEntity: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val packageName = text("packageName").references(ApplicationEntity.packageName)
    val title = varchar("title", 50)
    val videoUrl = text("videoUrl")
    val rating = double("rating")
}