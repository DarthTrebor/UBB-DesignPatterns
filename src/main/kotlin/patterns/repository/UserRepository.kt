package org.lab.patterns.repository

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.lab.models.User
import org.lab.tables.UsersTable

class UserRepository : IUserRepository
{
    override fun findByCredentials(username: String, password: String): User?
    {
        return transaction {
            User.find {
                (UsersTable.username eq username) and (UsersTable.password eq password)
            }.singleOrNull()
        }
    }
}