package ro.dobrescuandrei.jtextadventure.awtsample

import ro.andreidobrescu.base_sample.SampleTextAdventureGame
import ro.dobrescuandrei.jtextadventure.TextAdventureGameRunner
import ro.dobrescuandrei.jtextadventure.awt.AWTConsoleEmulator
import ro.dobrescuandrei.jtextadventure.awt.SimpleTextAdventureConsoleView
import java.awt.Dimension
import java.awt.Frame
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import kotlin.system.exitProcess

fun main()
{
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val windowSize = Dimension(screenSize.width, 150)
    val windowLocation = Point(0, 0)

    val window = Frame("Sample game")
    window.location = windowLocation
    window.size = windowSize
    window.layout = null
    window.isVisible = true

    val consoleView = SimpleTextAdventureConsoleView()
    consoleView.location = Point(0, 0)
    consoleView.size = windowSize
    window.add(consoleView)

    val consoleEmulator = AWTConsoleEmulator(consoleView = consoleView)
    val game = SampleTextAdventureGame(console = consoleEmulator)
    val gameRunner = TextAdventureGameRunner(console = consoleEmulator, game = game)
    gameRunner.start()

    window.addWindowListener(object : WindowAdapter() {
        override fun windowClosing(event : WindowEvent?) {
            gameRunner.dispose()
            exitProcess(status = 0)
        }
    })
}
