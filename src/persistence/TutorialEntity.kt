package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object TutorialEntity: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val packageName = varchar("packageName", 50).references(ApplicationEntity.packageName)
    val title = varchar("title", 50)
    val videoUrl = text("videoUrl")
    val rating = double("rating")
}