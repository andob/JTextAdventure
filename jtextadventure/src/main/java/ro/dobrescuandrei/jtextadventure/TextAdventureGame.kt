package ro.dobrescuandrei.jtextadventure

abstract class TextAdventureGame<CONSOLE : IConsoleEmulator>
(
    @JvmField
    val console : CONSOLE
) : Runnable
{
    fun interface IExceptionLogger { fun log(ex : Throwable) }

    var exceptionLogger : IExceptionLogger? =
        IExceptionLogger { it.printStackTrace() }

    var shouldTerminate : Boolean = false

    open fun dispose() {}
}
