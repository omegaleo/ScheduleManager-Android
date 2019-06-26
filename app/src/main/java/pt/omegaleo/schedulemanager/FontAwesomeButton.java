package pt.omegaleo.schedulemanager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;

public class FontAwesomeButton extends AppCompatButton {


    public FontAwesomeButton(Context context) {
        super(context);
        init(context);
    }

    public FontAwesomeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontAwesomeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context)
    {
        Typeface tf = ResourcesCompat.getFont(context,R.font.fontawesome_font);
        this.setTypeface(tf);
    }
}
