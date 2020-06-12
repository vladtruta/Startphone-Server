package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object ApplicationEntity: Table() {
    val packageName = text("packageName").primaryKey()
    val name = varchar("name", 50)
}