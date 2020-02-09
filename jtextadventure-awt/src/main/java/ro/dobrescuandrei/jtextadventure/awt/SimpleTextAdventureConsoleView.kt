package ro.dobrescuandrei.jtextadventure.awt

import java.awt.*

class SimpleTextAdventureConsoleView : TextAdventureConsoleView()
{
    private val labelsContainer : Panel
    private val buttonsContainer : Panel

    init
    {
        layout=GridLayout(2, 1)

        labelsContainer=Panel()
        labelsContainer.layout=CardLayout()
        add(labelsContainer)

        buttonsContainer=Panel()
        buttonsContainer.layout=FlowLayout()
        add(buttonsContainer)
    }

    override fun removeSubviews()
    {
        labelsContainer.removeAll()
        buttonsContainer.removeAll()
    }

    override fun addLabel(text : String)
    {
        labelsContainer.add(Button(text))
    }

    override fun addButton(text : String)
    {
        val button=Button(text)

        button.addActionListener { event ->
            consoleEmulator?.onButtonClicked(text)
        }

        buttonsContainer.add(button)
    }
}
