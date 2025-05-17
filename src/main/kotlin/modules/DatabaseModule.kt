package org.lab.modules

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val databaseModule = module {
    single {
        connect()
    }
}

fun connect(): Database
{
    return Database.connect(
        url = "jdbc:postgresql://localhost:5432/gdm",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = String()
    )
}