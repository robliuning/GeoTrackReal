package com.example.geotrackreal.customfont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTitleView extends TextView {

    public CustomTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTitleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                                               "fonts/impact.ttf");
        setTypeface(tf);
    }
}

