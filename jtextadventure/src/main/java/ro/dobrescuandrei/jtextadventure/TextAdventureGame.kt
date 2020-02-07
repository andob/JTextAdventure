package ro.dobrescuandrei.jtextadventure

abstract class TextAdventureGame<CONSOLE : IConsoleEmulator>
(
    @JvmField
    val console : CONSOLE
) : Runnable
{
    var exceptionLogger : ((Throwable) -> (Unit))? =
        { ex -> ex.printStackTrace() }

    open fun dispose() {}
}
