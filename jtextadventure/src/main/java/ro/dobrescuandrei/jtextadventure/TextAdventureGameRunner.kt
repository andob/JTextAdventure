package ro.dobrescuandrei.jtextadventure

class TextAdventureGameRunner
(
    val console : IConsoleEmulator,
    val game : TextAdventureGame
) : Thread()
{
    @Volatile private var isGameRunning = false
    fun isGameRunning() = isGameRunning

    override fun start()
    {
        if (isGameRunning)
            throw RuntimeException("Another game is already running!")

        super.start()
    }

    override fun run()
    {
        try
        {
            isGameRunning=true
            game.run()
        }
        catch (ex : Throwable)
        {
            game.exceptionLogger?.invoke(ex)
        }
        finally
        {
            isGameRunning=false
        }
    }

    fun dispose()
    {
        game.dispose()
        console.dispose()
        while(isGameRunning) {}
    }
}
