package org.lab.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.lab.models.User
import org.lab.patterns.repository.IUserRepository
import org.lab.patterns.repository.UserRepository

class LoginController {

    @FXML
    private lateinit var userField: TextField

    @FXML
    private lateinit var passField: PasswordField

    @FXML
    private lateinit var errorLabel: Label

    private val userRepo: IUserRepository = UserRepository()

    @FXML
    private fun handleLogin() {
        try {
            val username = userField.text.trim()
            val password = passField.text.trim()

            val user: User? = userRepo.findByCredentials(username, password)
            if (user == null) {
                errorLabel.text = "Invalid credentials!"
                return
            }

            val loader = FXMLLoader(javaClass.getResource("/chat-view.fxml"))
            val root: Parent = loader.load()
            val chatCtrl: ChatController = loader.getController()
            chatCtrl.setCurrentUser(user)

            val stage = userField.scene.window as Stage
            stage.title = "Chat – ${user.username}"
            stage.scene = Scene(root, 600.0, 600.0)
            stage.show()

        } catch (e: Exception) {
            errorLabel.text = "Error: ${e.message}"
            e.printStackTrace()
        }
    }
}
