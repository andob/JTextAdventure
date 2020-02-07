package ro.dobrescuandrei.jtextadventure

abstract class TextAdventureGame
(
    @JvmField
    val console : IConsoleEmulator
) : Runnable
{
    var exceptionLogger : ((Throwable) -> (Unit))? =
        { ex -> ex.printStackTrace() }

    open fun dispose() {}
}
