package ro.dobrescuandrei.jtextadventure.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public abstract class CustomView extends RelativeLayout
{
    public CustomView(Context context)
    {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public void init()
    {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutId(), this, true);
    }

    public abstract int getLayoutId();
}
