package org.lab.patterns.repository

import org.lab.models.Message
import org.lab.models.MessageRequest
import java.time.LocalDateTime

interface IMessageRepository
{
    fun save(message: MessageRequest): Message
    fun findAll(): List<Message>
    fun findAfter(since: LocalDateTime): List<Message>
}