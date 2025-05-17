package org.lab.patterns.singleton

import javafx.scene.Parent
import org.lab.patterns.strategy.DarkThemeStrategy
import org.lab.patterns.strategy.LightThemeStrategy
import org.lab.patterns.strategy.ThemeStrategy

class ThemeManager
{
    private val themes: Map<String, ThemeStrategy> = mapOf(
        "Light" to LightThemeStrategy(),
        "Dark" to DarkThemeStrategy()
    )

    private var root: Parent? = null

    fun init(root: Parent)
    {
        this.root = root
    }

    fun getAvailableThemes(): Set<String> = themes.keys

    fun applyTheme(name: String)
    {
        val r = root ?: return
        themes[name]?.applyTheme(r)
    }

    companion object
    {
        val instance = ThemeManager()
    }
}