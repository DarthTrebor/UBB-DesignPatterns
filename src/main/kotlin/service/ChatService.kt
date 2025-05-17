package org.lab.service

import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import org.lab.models.Message
import org.lab.models.MessageRequest
import org.lab.patterns.observer.MessageObserver
import org.lab.patterns.repository.IMessageRepository
import org.lab.patterns.repository.MessageRepository
import java.time.LocalDateTime
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class ChatService private constructor()
{

    private val repository: IMessageRepository = MessageRepository()
    private val messages: ObservableList<Message> = FXCollections.observableArrayList()
    private val observers: CopyOnWriteArrayList<MessageObserver> = CopyOnWriteArrayList()
    private val poller: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    @Volatile
    private var lastLoaded: LocalDateTime = LocalDateTime.MIN

    init
    {
        val init = repository.findAll()
        messages.addAll(init)
        if (init.isNotEmpty())
        {
            lastLoaded = init.last().timestamp
        }

        messages.addListener(ListChangeListener { change ->
            while (change.next()) {
                if (change.wasAdded()) {
                    change.addedSubList.forEach { msg ->
                        observers.forEach { it.onNewMessage(msg) }
                    }
                }
            }
        })
        poller.scheduleWithFixedDelay({ pollExternal() }, 1, 1, TimeUnit.SECONDS)
    }

    fun addObserver(observer: MessageObserver)
    {
        if (observers.contains(observer)) return
        observers.add(observer)
        messages.forEach(observer::onNewMessage)
    }

    @Synchronized
    fun sendMessage(message: MessageRequest)
    {
        val created = repository.save(message)
        messages.add(created)
        lastLoaded = created.timestamp
    }

    fun getAllMessages(): List<Message> = messages.toList()

    private fun pollExternal()
    {
        try
        {
            val newer = repository.findAfter(lastLoaded)
            if (newer.isNotEmpty())
            {
                Platform.runLater {
                    messages.addAll(newer)
                }
                lastLoaded = newer.last().timestamp
            }
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
    }

    companion object
    {
        val instance = ChatService()
    }
}