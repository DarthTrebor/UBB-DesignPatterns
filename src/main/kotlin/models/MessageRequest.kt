package org.lab.models

import java.time.LocalDateTime

data class MessageRequest(
    val content: String,
    val sender: User,
    val timestamp: LocalDateTime
)