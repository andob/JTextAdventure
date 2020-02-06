package ro.dobrescuandrei.jtextadventure

abstract class TextAdventureGame
(
    val console : IConsoleEmulator
) : Runnable
{
    var exceptionLogger : ((Throwable) -> (Unit))? =
        { ex -> ex.printStackTrace() }

    open fun dispose() {}
}
