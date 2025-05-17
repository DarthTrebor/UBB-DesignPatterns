package org.lab.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.lab.tables.MessagesTable
import java.time.LocalDateTime
import java.util.UUID

class Message(id: EntityID<UUID>) : UUIDEntity(id)
{
    companion object : UUIDEntityClass<Message>(MessagesTable)

    var content by MessagesTable.content
    var sender by MessagesTable.sender
    var timestamp by MessagesTable.timestamp

    override fun toString(): String
    {
        return "[$timestamp] ${sender}: $content"
    }

    constructor(content: String, sender: UUID, timestamp: LocalDateTime) :
            this(EntityID(UUID.randomUUID(), MessagesTable))
    {
        this.content = content
        this.sender = sender
        this.timestamp = timestamp
    }
}