package ro.dobrescuandrei.jtextadventure.clisample

import ro.andreidobrescu.base_sample.SampleTextAdventureGame
import ro.dobrescuandrei.jtextadventure.TextAdventureGameRunner
import ro.dobrescuandrei.jtextadventure.cli.CLIConsoleEmulator

fun main()
{
    val consoleEmulator=CLIConsoleEmulator()
    val game=SampleTextAdventureGame(console = consoleEmulator)
    val gameRunner=TextAdventureGameRunner(console = consoleEmulator, game = game)

    gameRunner.start()
    gameRunner.join()

    gameRunner.dispose()
    consoleEmulator.dispose()
}
