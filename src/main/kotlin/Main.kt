package org.lab

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.jetbrains.exposed.sql.Database
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.lab.modules.databaseModule

class Main : Application()
{
    override fun start(stage: Stage)
    {
        startKoin {
            modules(databaseModule)
        }

        getKoin().get<Database>() // inject database

        val fxmlLoader = FXMLLoader(javaClass.getResource("/login-view.fxml"))
        val root = fxmlLoader.load<javafx.scene.Parent>()
        val scene = Scene(root, 600.0, 600.0)
        stage.title = "Fidessa Chat"
        stage.scene = scene
        stage.show()
    }
}

fun main(args: Array<String>)
{
    Application.launch(Main::class.java, *args)
}
