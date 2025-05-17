package org.lab.patterns.repository

import org.lab.models.User

interface IUserRepository
{
    fun findByCredentials(username: String, password: String): User?
}