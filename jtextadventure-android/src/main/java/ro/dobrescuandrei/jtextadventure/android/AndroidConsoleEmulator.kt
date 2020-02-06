package ro.dobrescuandrei.jtextadventure.android

import android.app.Activity
import ro.dobrescuandrei.jtextadventure.IConsoleEmulator
import java.util.*

class AndroidConsoleEmulator
(
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
        (consoleView.context as Activity).runOnUiThread { toRun() }

    override fun write(message : String)
    {
        runOnUiThread {
            consoleView.addTextView(message)
            consoleView.requestLayout()
        }
    }

    override fun promptButtons(vararg buttons : String)
    {
        runOnUiThread {
            for (buttonText in buttons)
            {
                consoleView.addButton(buttonText)
                consoleView.requestLayout()
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
            consoleView.requestLayout()
        }

        return buttonText
    }

    fun onButtonClicked(buttonText : String)
    {
        inputQueue.add(buttonText)
    }
}
