package org.lab.models

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.lab.tables.UsersTable
import java.util.UUID

class User(id: EntityID<UUID>) : Entity<UUID>(id)
{
    companion object : EntityClass<UUID, User>(UsersTable)

    var username by UsersTable.username
    var password by UsersTable.password
}
