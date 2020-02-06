package ro.dobrescuandrei.jtextadventure

interface IConsoleEmulator
{
    fun write(message : String)
    fun promptButtons(vararg buttons : String)

    fun read() : String

    fun dispose()
}
