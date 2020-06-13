package com.vladtruta.di

import com.vladtruta.persistence.ApplicationDao
import com.vladtruta.persistence.IAppDao
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase() }

    single<IAppDao> { ApplicationDao(get()) }
}

private fun provideDatabase(): Database {
    return Database.connect(
        "jdbc:mysql://localhost:3306/startphone?serverTimezone=UTC",
        "com.mysql.cj.jdbc.Driver",
        "root",
        "toor"
    )
}