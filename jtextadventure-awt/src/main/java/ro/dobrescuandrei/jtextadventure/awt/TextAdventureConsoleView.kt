package ro.dobrescuandrei.jtextadventure.awt

import java.awt.Panel

abstract class TextAdventureConsoleView : Panel()
{
    var consoleEmulator : AWTConsoleEmulator? = null

    abstract fun removeSubviews()
    abstract fun addLabel(text: String)
    abstract fun addButton(text: String)
}
