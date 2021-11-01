package ro.dobrescuandrei.jtextadventure

abstract class TextAdventureGame<CONSOLE : IConsoleEmulator>
(
    @JvmField
    val console : CONSOLE
) : Runnable
{
    fun interface ExceptionLogger { fun log(ex : Throwable) }

    var exceptionLogger : ExceptionLogger? =
        ExceptionLogger { it.printStackTrace() }

    var shouldTerminate : Boolean = false

    open fun dispose() {}
}
