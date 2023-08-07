package ro.dobrescuandrei.jtextadventure.android

import android.app.Activity
import ro.dobrescuandrei.jtextadventure.IConsoleEmulator
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean

class AndroidConsoleEmulator
(
    @JvmField
    val consoleView : TextAdventureConsoleView
) : IConsoleEmulator
{
    private val shouldDisposeStdout = AtomicBoolean(false)

    private val stdin = LinkedBlockingQueue<String>(Int.MAX_VALUE)

    private fun eof() = (0x04).toChar().toString()

    init
    {
        consoleView.consoleEmulator = this
    }

    override fun dispose()
    {
        stdin.add(eof())
        shouldDisposeStdout.set(true)
        consoleView.consoleEmulator = null
    }

    private inline fun runOnUiThread(crossinline toRun : () -> (Unit)) =
        (consoleView.context as Activity).runOnUiThread { toRun() }

    override fun write(message : String)
    {
        if (shouldDisposeStdout.get())
            throw Throwable("IO disposed")

        runOnUiThread {
            consoleView.addTextView(message)
            consoleView.requestLayout()
        }
    }

    override fun promptButtons(vararg buttons : String)
    {
        if (shouldDisposeStdout.get())
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
        val line = stdin.take()
        if (line==eof())
            return ""

        runOnUiThread {
            consoleView.removeSubviews()
            consoleView.requestLayout()
        }

        return line
    }

    fun onButtonClicked(buttonText : String)
    {
        stdin.add(buttonText)
    }
}
