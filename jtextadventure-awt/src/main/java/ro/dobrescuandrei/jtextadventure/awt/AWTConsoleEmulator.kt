package ro.dobrescuandrei.jtextadventure.awt

import ro.dobrescuandrei.jtextadventure.IConsoleEmulator
import java.awt.EventQueue
import java.util.concurrent.LinkedBlockingQueue

class AWTConsoleEmulator
(
    @JvmField
    val consoleView : TextAdventureConsoleView
) : IConsoleEmulator
{
    private val stdin = LinkedBlockingQueue<String>(Int.MAX_VALUE)

    private fun eof() = (0x04).toChar().toString()

    init
    {
        consoleView.consoleEmulator = this
    }

    override fun dispose()
    {
        stdin.add(eof())
        consoleView.consoleEmulator = null
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
        val line = stdin.take()
        if (line == eof())
            return ""

        runOnUiThread {
            consoleView.removeSubviews()
            consoleView.revalidate()
            consoleView.repaint()
        }

        return line
    }

    fun onButtonClicked(buttonText : String)
    {
        stdin.add(buttonText)
    }
}
