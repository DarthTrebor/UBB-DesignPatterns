package org.lab.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object MessagesTable : UUIDTable("messages")
{
    val content = text("content")
    val sender = uuid("sender_id")
    val timestamp = datetime("timestamp")
}