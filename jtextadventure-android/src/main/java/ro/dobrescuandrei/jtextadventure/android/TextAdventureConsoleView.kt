package ro.dobrescuandrei.jtextadventure.android

import android.content.Context
import android.util.AttributeSet

abstract class TextAdventureConsoleView : CustomView
{
    var consoleEmulator : AndroidConsoleEmulator? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    abstract fun removeSubviews()
    abstract fun addTextView(text: String)
    abstract fun addButton(text: String)

    override fun onDetachedFromWindow()
    {
        consoleEmulator=null
        super.onDetachedFromWindow()
    }
}
