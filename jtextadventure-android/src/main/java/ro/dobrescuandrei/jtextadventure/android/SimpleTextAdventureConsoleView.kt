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
    private var textViewsContainer : LinearLayout? = null
    private var buttonsContainer : LinearLayout? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun init()
    {
        super.init()

        textViewsContainer=findViewById(R.id.textViewsContainer)
        buttonsContainer=findViewById(R.id.buttonsContainer)
    }

    override fun onDetachedFromWindow()
    {
        textViewsContainer=null
        buttonsContainer=null

        super.onDetachedFromWindow()
    }

    override fun getLayoutId() = R.layout.simple_text_adventure_console_view

    override fun removeSubviews()
    {
        textViewsContainer?.removeAllViews()
        buttonsContainer?.removeAllViews()
    }

    override fun addTextView(text : String)
    {
        val textView=TextView(context)
        textView.layoutParams=LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
        textView.setTextColor(Color.BLACK)
        textView.text=text
        textViewsContainer?.addView(textView)
    }

    override fun addButton(text : String)
    {
        val button=Button(context)
        button.layoutParams=LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
        button.setTextColor(Color.BLACK)
        button.text=text
        button.isAllCaps=false
        buttonsContainer?.addView(button)

        button.setOnClickListener {
            consoleEmulator?.onButtonClicked(text)
        }
    }
}
