package org.lab.controller

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*
import org.lab.models.Message
import org.lab.models.User
import org.lab.patterns.singleton.ThemeManager
import org.lab.patterns.builder.MessageBuilder
import org.lab.patterns.observer.MessageObserver
import org.lab.service.ChatService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatController : MessageObserver {

    @FXML
    private lateinit var rootPane: BorderPane

    @FXML
    private lateinit var messageContainer: VBox

    @FXML
    private lateinit var messageField: TextField

    @FXML
    private lateinit var themeChoice: ChoiceBox<String>

    private val service = ChatService.instance
    private lateinit var currentUser: User
    private val timeFmt: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    @FXML
    fun initialize() {
        val tm = ThemeManager.instance
        tm.init(rootPane)

        themeChoice.items.addAll(tm.getAvailableThemes())
        themeChoice.value = "Light"
        tm.applyTheme("Light")

        themeChoice.selectionModel.selectedItemProperty().addListener { _, _, newTheme ->
            tm.applyTheme(newTheme)
        }
    }

    fun setCurrentUser(user: User) {
        this.currentUser = user
        service.addObserver(this)
    }

    override fun onNewMessage(message: Message) {
        refreshChat()
    }

    @FXML
    fun handleSend(evt: ActionEvent) {
        if (messageField.text.isBlank()) return

        val msg = MessageBuilder()
            .setSender(currentUser)
            .setContent(messageField.text)
            .setTimestamp(LocalDateTime.now())
            .build()

        service.sendMessage(msg)
        messageField.clear()
    }

    private fun refreshChat() {
        Platform.runLater {
            messageContainer.children.clear()
            service.getAllMessages().forEach { message ->
                messageContainer.children.add(buildBubble(message))
            }
        }
    }

    private fun buildBubble(message: Message): HBox {
        val mine = message.sender == currentUser.id.value
        val box = HBox(8.0).apply {
            maxWidth = Double.MAX_VALUE
            HBox.setHgrow(this, Priority.ALWAYS)
        }

        val spacer = Region().apply {
            HBox.setHgrow(this, Priority.ALWAYS)
        }

        val text = Label(message.content).apply {
            styleClass.addAll("message-bubble", if (mine) "mine" else "their")
            isWrapText = true
            maxWidthProperty().bind(messageContainer.widthProperty().multiply(0.6))
        }

        val time = Label(message.timestamp.format(timeFmt)).apply {
            styleClass.add("time-label")
        }

        if (mine) {
            box.alignment = Pos.CENTER_RIGHT
            box.children.setAll(spacer, text, time)
        } else {
            box.alignment = Pos.CENTER_LEFT
            box.children.setAll(time, text, spacer)
        }

        return box
    }
}
