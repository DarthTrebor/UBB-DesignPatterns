package org.lab.patterns.strategy

import javafx.scene.Parent

class LightThemeStrategy : ThemeStrategy
{
    override fun applyTheme(root: Parent)
    {
        root.stylesheets.setAll(
            javaClass.getResource("/light.css")!!.toExternalForm()
        )
    }
}