package ro.dobrescuandrei.jtextadventure.android

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class SimpleTextAdventureConsoleView : TextAdventureConsoleView
{
    constructor(context : Context?) : super(context)
    constructor(context : Context?, attrs : AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.simple_text_adventure_console_view

    override fun removeSubviews()
    {
        val textViewsContainer = findViewById<LinearLayout>(R.id.textViewsContainer)
        val buttonsContainer = findViewById<LinearLayout>(R.id.buttonsContainer)

        textViewsContainer?.removeAllViews()
        buttonsContainer?.removeAllViews()
    }

    override fun addTextView(text : String)
    {
        val textViewsContainer = findViewById<LinearLayout>(R.id.textViewsContainer)

        val textView = TextView(context)
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
        textView.setTextColor(Color.BLACK)
        textView.text = text
        textViewsContainer?.addView(textView)
    }

    override fun addButton(text : String)
    {
        val buttonsContainer = findViewById<LinearLayout>(R.id.buttonsContainer)

        val button = Button(context)
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
        button.setTextColor(Color.BLACK)
        button.text = text
        button.isAllCaps = false
        buttonsContainer?.addView(button)

        button.setOnClickListener {
            consoleEmulator?.onButtonClicked(text)
        }
    }
}
