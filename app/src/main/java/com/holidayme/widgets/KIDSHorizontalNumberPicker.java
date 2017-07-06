package com.holidayme.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.holidayme.AppInterface.OnKidsCountChnageListener;
import com.holidayme.activities.R;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.Constants.Constant;

/**
 * Created by supriya.sakore on 10-02-2016.
 */
public class KIDSHorizontalNumberPicker extends LinearLayout {
    private EditText numberEditText;
    private ImageView addButtonImageView, minusButtonImageView;
    private int DEFAULT_MAX = 2;
    private int DEFAULT_MIN = 0;
    private OnKidsCountChnageListener onKidsCountChnageListener;

    public void setOnKidsCountChangeListener(OnKidsCountChnageListener onKidsCountChangeListener) {
        this.onKidsCountChnageListener = onKidsCountChangeListener;
    }

    public KIDSHorizontalNumberPicker(Context context) {
        super(context);
        Initialisewigde(context);
    }

    public KIDSHorizontalNumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialisewigde(context);
    }

    public KIDSHorizontalNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialisewigde(context);
    }

    @SuppressLint("SetTextI18n")
    private void Initialisewigde(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.horizontal_numberpicker, this);

        addButtonImageView = (ImageView) findViewById(R.id.addButtonImageView);
        minusButtonImageView = (ImageView) findViewById(R.id.minusButtonImageView);

        numberEditText = (EditText) findViewById(R.id.numberEditText);
        HolidayMeFont.overrideFonts(context, numberEditText, Constant.HelveticaNeueRoman);
        numberEditText.setText("0");

        addButtonImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String digit = numberEditText.getText().toString();
                int number = Integer.valueOf(digit);
                if (number < DEFAULT_MAX) {
                    number = number + 1;
                    numberEditText.setText(number + "");
                    if (onKidsCountChnageListener != null)
                        onKidsCountChnageListener.currentKidCount(number-1,number);
                }
            }
        });
        minusButtonImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberEditText.getText().toString());

                if (number > DEFAULT_MIN) {
                    number = number - 1;
                    numberEditText.setText(number + "");
                    if (onKidsCountChnageListener != null)
                        onKidsCountChnageListener.currentKidCount(number+1,number);
                }
            }

        });
    }
    @SuppressLint("SetTextI18n")
    public void setDigit(int count)
    {
        numberEditText.setText(""+count);
    }
}
