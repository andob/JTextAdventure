package ro.andreidobrescu.base_sample

import ro.dobrescuandrei.jtextadventure.IConsoleEmulator
import ro.dobrescuandrei.jtextadventure.TextAdventureGame

class SampleTextAdventureGame : TextAdventureGame<IConsoleEmulator>
{
    constructor(console: IConsoleEmulator) : super(console)

    override fun run() = resetGame()

    fun resetGame()
    {
        showFirstRoom(
            onPlayerGoesToLeft = {
                console.write("Game over! Replay???")
                console.promptButtons("yes", "no")
                if (console.read().toLowerCase()=="yes")
                    resetGame()
            },
            onPlayerGoesToRight = {
                showOutsideOfTheHouse(
                    onPlayerGoesBack = {
                        resetGame()
                    })
            })
    }

    fun showFirstRoom(onPlayerGoesToLeft : () -> (Unit),
                      onPlayerGoesToRight : () -> (Unit))
    {
        console.write("You are in a room. There is a door to the left and another door to the right")
        console.promptButtons("go left", "go right")
        when (console.read().toLowerCase())
        {
            "go left" -> onPlayerGoesToLeft()
            "go right" -> onPlayerGoesToRight()
        }
    }

    fun showOutsideOfTheHouse(onPlayerGoesBack : () -> (Unit))
    {
        console.write("You are outside of the house")
        console.promptButtons("go back")
        if (console.read().toLowerCase()=="go back")
            onPlayerGoesBack()
    }
}
