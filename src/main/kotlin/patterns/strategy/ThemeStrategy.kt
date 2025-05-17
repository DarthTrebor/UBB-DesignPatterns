package org.lab.patterns.strategy

import javafx.scene.Parent

interface ThemeStrategy
{
    fun applyTheme(root: Parent)
}