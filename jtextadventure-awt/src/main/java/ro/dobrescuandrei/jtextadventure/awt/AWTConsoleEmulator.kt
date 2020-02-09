package ro.dobrescuandrei.jtextadventure.awt

import ro.dobrescuandrei.jtextadventure.IConsoleEmulator
import java.awt.EventQueue
import java.util.*

open class AWTConsoleEmulator
(
    @JvmField
    val consoleView : TextAdventureConsoleView
) : IConsoleEmulator
{
    private val inputQueue = LinkedList<String>()
    private var shouldDisposeInputReadOperations = false

    init
    {
        consoleView.consoleEmulator=this
    }

    override fun dispose()
    {
        shouldDisposeInputReadOperations=true
        consoleView.consoleEmulator=null
    }

    private inline fun runOnUiThread(crossinline toRun : () -> (Unit)) =
        EventQueue.invokeLater { toRun() }

    override fun write(message : String)
    {
        runOnUiThread {
            consoleView.addLabel(message)
            consoleView.revalidate()
            consoleView.repaint()
        }
    }

    override fun promptButtons(vararg buttons : String)
    {
        runOnUiThread {
            for (buttonText in buttons)
            {
                consoleView.addButton(buttonText)
                consoleView.revalidate()
                consoleView.repaint()
            }
        }
    }

    override fun read() : String
    {
        if (shouldDisposeInputReadOperations)
            return ""

        while (inputQueue.isEmpty())
        {
            if (shouldDisposeInputReadOperations)
                return ""

            Thread.sleep(1)
        }

        val buttonText=inputQueue.remove()!!

        runOnUiThread {
            consoleView.removeSubviews()
            consoleView.revalidate()
            consoleView.repaint()
        }

        return buttonText
    }

    fun onButtonClicked(buttonText : String)
    {
        inputQueue.add(buttonText)
    }
}
