package org.lab.patterns.repository

import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import org.lab.models.Message
import org.lab.models.MessageRequest
import org.lab.tables.MessagesTable
import java.time.LocalDateTime

class MessageRepository : IMessageRepository
{

    override fun save(message: MessageRequest): Message
    {
        return transaction {
            Message.new {
                content = message.content
                sender = message.sender.id.value
                timestamp = message.timestamp
            }
        }
    }

    override fun findAll(): List<Message>
    {
        return transaction {
            Message.all()
                .orderBy(MessagesTable.timestamp to SortOrder.ASC)
                .toList()
        }
    }

    override fun findAfter(since: LocalDateTime): List<Message>
    {
        return transaction {
            Message.find { MessagesTable.timestamp greater since }
                .orderBy(MessagesTable.timestamp to SortOrder.ASC)
                .toList()
        }
    }
}