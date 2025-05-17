package org.lab.patterns.observer

import org.lab.models.Message

interface MessageObserver
{
    fun onNewMessage(message: Message)
}