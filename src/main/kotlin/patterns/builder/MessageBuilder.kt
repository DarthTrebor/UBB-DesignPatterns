package org.lab.patterns.builder

import org.lab.models.MessageRequest
import org.lab.models.User
import java.time.LocalDateTime

interface IMessageBuilder
{
    fun build(): MessageRequest
    fun setContent(content: String): MessageBuilder
    fun setSender(sender: User): MessageBuilder
    fun setTimestamp(timestamp: LocalDateTime): MessageBuilder
}

class MessageBuilder : IMessageBuilder
{
    private var content: String? = null
    private var sender: User? = null
    private var timestamp: LocalDateTime? = null

    override fun setContent(content: String) = apply {
        this.content = content
    }

    override fun setSender(sender: User) = apply {
        this.sender = sender
    }

    override fun setTimestamp(timestamp: LocalDateTime) = apply {
        this.timestamp = timestamp
    }

    override fun build(): MessageRequest
    {
        if (content == null)
        {
            throw IllegalArgumentException("content must not be null")
        }
        if (sender == null)
        {
            throw IllegalArgumentException("sender must not be null")
        }
        if (timestamp == null)
        {
            throw IllegalArgumentException("timestamp must not be null")
        }
        return MessageRequest(content!!, sender!!, timestamp!!)
    }
}