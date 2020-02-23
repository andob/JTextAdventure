package ro.dobrescuandrei.jtextadventure.android

import android.app.Activity
import ro.dobrescuandrei.jtextadventure.IConsoleEmulator
import java.util.*

open class AndroidConsoleEmulator
(
    @JvmField
    val consoleView : TextAdventureConsoleView
) : IConsoleEmulator
{
    private val inputQueue = LinkedList<String>()
    private var shouldDisposeIO = false

    init
    {
        consoleView.consoleEmulator=this
    }

    override fun dispose()
    {
        shouldDisposeIO=true
        consoleView.consoleEmulator=null
    }

    private inline fun runOnUiThread(crossinline toRun : () -> (Unit)) =
        (consoleView.context as Activity).runOnUiThread { toRun() }

    override fun write(message : String)
    {
        if (shouldDisposeIO)
            throw Throwable("IO disposed")

        runOnUiThread {
            consoleView.addTextView(message)
            consoleView.requestLayout()
        }
    }

    override fun promptButtons(vararg buttons : String)
    {
        if (shouldDisposeIO)
            throw Throwable("IO disposed")

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
        if (shouldDisposeIO)
            throw Throwable("IO disposed")

        while (inputQueue.isEmpty())
        {
            if (shouldDisposeIO)
                throw Throwable("IO disposed")

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
