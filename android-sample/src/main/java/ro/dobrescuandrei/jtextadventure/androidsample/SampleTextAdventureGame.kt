package ro.dobrescuandrei.jtextadventure.androidsample

import android.content.Context
import ro.dobrescuandrei.jtextadventure.TextAdventureGame
import ro.dobrescuandrei.jtextadventure.android.AndroidConsoleEmulator

class SampleTextAdventureGame
(
    console : AndroidConsoleEmulator,
    var context : Context?
) : TextAdventureGame<AndroidConsoleEmulator>(console)
{
    override fun run() = resetGame()

    fun resetGame()
    {
        console.write(context!!.getString(R.string.app_name))

        showFirstRoom(
            onPlayerGoesToLeft = {
                console.write("Game over! Replay???")
                console.promptButtons("yes", "no")
                if (console.read().toLowerCase()=="yes")
                    resetGame()
                else console.write("Game over!")
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

    override fun dispose()
    {
        context=null
    }
}
