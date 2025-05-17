package org.lab.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object UsersTable : UUIDTable("users")
{
    val username = varchar("username", 50).uniqueIndex()
    val password = varchar("password", 64)
}