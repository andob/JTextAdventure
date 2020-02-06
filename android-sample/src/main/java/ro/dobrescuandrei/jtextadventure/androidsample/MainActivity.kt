package ro.dobrescuandrei.jtextadventure.androidsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ro.dobrescuandrei.jtextadventure.TextAdventureGameRunner
import ro.dobrescuandrei.jtextadventure.android.AndroidConsoleEmulator
import ro.dobrescuandrei.jtextadventure.android.SimpleTextAdventureConsoleView

class MainActivity : AppCompatActivity()
{
    val gameRunner : TextAdventureGameRunner by lazy {
        val consoleView=findViewById<SimpleTextAdventureConsoleView>(R.id.consoleView)!!
        val consoleEmulator=AndroidConsoleEmulator(consoleView = consoleView)
        val game=SampleTextAdventureGame(console = consoleEmulator, context = this)
        TextAdventureGameRunner(console = consoleEmulator, game = game)
    }

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_activity)
        gameRunner.start()
    }

    override fun onDestroy()
    {
        gameRunner.dispose()
        super.onDestroy()
    }
}
