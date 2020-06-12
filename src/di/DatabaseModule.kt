package com.vladtruta.di

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase() }
}

private fun provideDatabase(): Database {
    return Database.connect(
        "jdbc:mysql://localhost:3306/restaurant?serverTimezone=UTC",
        "com.mysql.cj.jdbc.Driver",
        "root",
        "12345678"
    )
}