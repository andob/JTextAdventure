package ro.dobrescuandrei.jtextadventure.cli

import ro.dobrescuandrei.jtextadventure.IConsoleEmulator
import java.util.*

class CLIConsoleEmulator : IConsoleEmulator
{
    private val stdinScanner = Scanner(System.`in`)

    private var promptedButtons : List<String>? = null

    override fun write(message : String)
    {
        println(message)
    }

    override fun promptButtons(vararg buttons : String)
    {
        promptedButtons = buttons.toList()

        println("[${buttons.joinToString(separator = " / ")}]")
    }

    override fun read() : String
    {
        while(true)
        {
            val readLine = stdinScanner.nextLine()!!
            if (promptedButtons == null || promptedButtons?.isEmpty() == true ||
               (promptedButtons != null && readLine in promptedButtons!!))
                return readLine
        }
    }

    override fun dispose()
    {
        stdinScanner.close()
    }
}
