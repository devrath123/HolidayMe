package com.holidayme.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.holidayme.activities.R;

/**
 * Created by devrath.rathee on 29-11-2016.
 */

public class CustomSeekBar extends SeekBar {

    Context context;

    public CustomSeekBar(Context context) {
        super(context);
        this.context = context;
        initialiseUI();
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialiseUI();
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initialiseUI();
    }


    public void initialiseUI() {

        this.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape));
        this.setProgressDrawable(getResources().getDrawable(R.drawable.rounded_corner));
    }

}
