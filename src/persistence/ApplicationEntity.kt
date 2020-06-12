package com.vladtruta.persistence

import org.jetbrains.exposed.sql.Table

object ApplicationEntity: Table() {
    val packageName = varchar("packageName", 50).primaryKey()
    val name = varchar("name", 50)
}