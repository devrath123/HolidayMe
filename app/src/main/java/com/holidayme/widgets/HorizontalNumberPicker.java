package com.holidayme.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.holidayme.AppInterface.OnAdultsCountChnageListener;
import com.holidayme.activities.R;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.Constants.Constant;

/**
 * Created by supriya.sakore on 10-02-2016.
 */
public class HorizontalNumberPicker extends LinearLayout {
    public EditText digit;
    ImageView add, sub;
    public int DEFAULT_MAX=4;
    public int DEFAULT_MIN=1;
    Context context;
    public OnAdultsCountChnageListener onAdultsCountChangeListener;

    public void setOnAdultsCountChangeListener(OnAdultsCountChnageListener onAdultsCountChangeListener) {
        this.onAdultsCountChangeListener = onAdultsCountChangeListener;
    }

    public HorizontalNumberPicker(Context context) {
        super(context);
        Initialisewigde(context);
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialisewigde(context);
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialisewigde(context);
    }

    @SuppressLint("SetTextI18n")
    public void Initialisewigde(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.horizontal_numberpicker, this);
        add = (ImageView) findViewById(R.id.addButtonImageView);
        sub = (ImageView) findViewById(R.id.minusButtonImageView);
        digit = (EditText) findViewById(R.id.numberEditText);
        HolidayMeFont.overrideFonts(context, digit, Constant.HelveticaNeueRoman);

        digit.setText("2");

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String digi = digit.getText().toString();
                int no = Integer.valueOf(digi);
                if (no < DEFAULT_MAX) {
                    no = no + 1;
                    digit.setText(no + "");
                    if (onAdultsCountChangeListener!=null)
                        onAdultsCountChangeListener.currentAdultCount(no-1,no);
                }

            }
        });
        sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int no = Integer.parseInt(digit.getText().toString());

                if (no > DEFAULT_MIN) {
                    no = no - 1;
                    digit.setText(no + "");
                    if (onAdultsCountChangeListener!=null)
                        onAdultsCountChangeListener.currentAdultCount(no+1,no);
                }

            }

        });
    }
    @SuppressLint("SetTextI18n")
    public void setDigit(int count)
    {
        digit.setText(""+count);
    }
}
