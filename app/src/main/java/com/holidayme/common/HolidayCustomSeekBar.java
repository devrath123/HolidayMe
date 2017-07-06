package com.holidayme.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.holidayme.activities.R;

/**
 * Created by devrath.rathee on 06-07-2017.
 */

public class HolidayCustomSeekBar extends SeekBar {

    Context mContext;

    public HolidayCustomSeekBar(Context context)
    {
        super(context);
        this.mContext = context;
        initialiseUI();
    }

    public HolidayCustomSeekBar(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        this.mContext = context;
        initialiseUI();
    }

    public HolidayCustomSeekBar(Context context,AttributeSet attributeSet,int defaultSetAttribute)
    {
        super(context,attributeSet,defaultSetAttribute);
        this.mContext = context;
        initialiseUI();
    }

    private void initialiseUI()
    {
        this.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape));
        this.setProgressDrawable(getResources().getDrawable(R.drawable.holiday_popular_rounded_corner));
    }
}
