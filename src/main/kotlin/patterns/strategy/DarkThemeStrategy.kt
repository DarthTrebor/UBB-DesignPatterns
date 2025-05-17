package org.lab.patterns.strategy

import javafx.scene.Parent

class DarkThemeStrategy : ThemeStrategy
{
    override fun applyTheme(root: Parent)
    {
        root.stylesheets.setAll(
            javaClass.getResource("/dark.css")!!.toExternalForm()
        )
    }
}