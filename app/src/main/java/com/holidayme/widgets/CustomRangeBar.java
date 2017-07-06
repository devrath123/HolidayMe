package com.holidayme.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.holidayme.activities.R;
import com.holidayme.Constants.Constant;
import com.holidayme.data.UserDTO;

public class CustomRangeBar extends ImageView {

    private Bitmap thumbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.range);
    private int thumb1X, thumb2X,xFactor, textXFactor,pixelsPerSlot,maximumRangePosition,minimumRangePosition,selectedMin,selectedMax,selectedThumb,thumbHalfWidth,min,max;
    private String thumbOneValue, thumbTwoValue, Currency,language;
    private Paint paint = new Paint();
    private SeekBarChangeListener seekBarChangeListener;
    private Paint rightTextPaint = new Paint();
    private Paint leftTextPaint = new Paint();
    Context context;
    private double range;
    private boolean isCheck = false,isFirstTime = true;


    public CustomRangeBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public CustomRangeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomRangeBar(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (getHeight() > 0)
            init();

    }

    public void setMinMax(int min, int max, String Currency) {
        this.min = min;
        this.max = max;
        this.Currency = Currency;
        invalidate();
    }

    public void setPreSelectedMinMax(int selectedMin, int selectedMax) {
        this.selectedMin = selectedMin;
        this.selectedMax = selectedMax;
        invalidate();
    }

    private void init() {

        language = UserDTO.getUserDTO().getLanguage();
        rightTextPaint.setTextSize((int) (0.04 * getWidth()));

        leftTextPaint.setColor(ContextCompat.getColor(context,R.color.light_purple));
        leftTextPaint.setTextAlign(Paint.Align.LEFT);
        leftTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        leftTextPaint.setTextSize((int) (0.06 * getWidth()));
        if(language.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
        thumbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.range);
        else
            thumbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.range);
        minimumRangePosition = (int) ((0.0714285714285714) * getHeight());
        maximumRangePosition = (int) ((0.9242857142857143) * getHeight());
        pixelsPerSlot = (int) ((0.0942857142857143) * getHeight());

        if (thumbBitmap.getWidth() > getWidth())
            getLayoutParams().width = thumbBitmap.getWidth();

        thumbHalfWidth = thumbBitmap.getWidth() / 2;
        thumb1X = thumbHalfWidth;
        thumb2X = getHeight();

        if (language.equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)) {
            xFactor = (int) (0.50 * getWidth()) - thumbHalfWidth;
            textXFactor = (int) (0.50 * getWidth()) + thumbHalfWidth;
            rightTextPaint.setTextAlign(Paint.Align.LEFT);
        } else {
            xFactor = (int) (0.51 * getWidth()) - thumbHalfWidth;
            textXFactor = (int) (0.40 * getWidth()) - thumbHalfWidth;
            rightTextPaint.setTextAlign(Paint.Align.RIGHT);
        }
        range = (max - min) / 9;
        thumbOneValue = Integer.toString(min);
        thumbTwoValue = Integer.toString(max);

        if (selectedMin != 0) {
            selectedThumb = 1;
            thumb1X = selectedMin;
            calculateThumbValue();
            selectedMin = 0;
        }
        if (selectedMax != 0) {
            selectedThumb = 2;
            thumb2X = selectedMax;
            calculateThumbValue();
            selectedMax = 0;
        }
        invalidate();
    }

    public void setSeekBarChangeListener(SeekBarChangeListener scl) {
        this.seekBarChangeListener = scl;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (thumbOneValue == null || thumbTwoValue == null) {
            isCheck = false;
            initView(canvas);
        } else if (TextUtils.equals(thumbOneValue, Integer.toString(min)) && TextUtils.equals(thumbTwoValue, Integer.toString(max))) {
            isCheck = false;
            initView(canvas);
        } else {
            isCheck = true;
            canvas.drawBitmap(thumbBitmap, xFactor, thumb1X, paint);
            canvas.drawBitmap(thumbBitmap, xFactor, thumb2X - thumbHalfWidth, paint);
            if (language.equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)) {
                canvas.drawText(thumbOneValue, thumbBitmap.getWidth() / 2, thumb1X + thumbBitmap.getHeight() / 2 + 10, leftTextPaint);
                canvas.drawText(thumbTwoValue, thumbBitmap.getWidth() / 2, thumb2X + 10, leftTextPaint);
            } else {
                canvas.drawText(thumbOneValue, (int) (0.65 * getWidth()) + thumbHalfWidth, thumb1X + thumbBitmap.getHeight() / 2 + 10, leftTextPaint);
                canvas.drawText(thumbTwoValue, (int) (0.65 * getWidth()) + thumbHalfWidth, thumb2X + 10, leftTextPaint);
            }
        }

        double sum = 0;
        float top = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                sum = min;
                top = pixelsPerSlot;
                rightTextPaint.setColor(Color.BLACK);
                canvas.drawText(Currency.toUpperCase() + " " + (int) sum, textXFactor + thumbHalfWidth, top, rightTextPaint);
            } else if (i == 9) {
                sum = max;
                top = top + pixelsPerSlot;
                rightTextPaint.setColor(Color.BLACK);
                canvas.drawText(Currency.toUpperCase() + " " + (int) sum, textXFactor + thumbHalfWidth, top, rightTextPaint);
            } else {
                sum = sum + range;
                top = top + pixelsPerSlot;
                rightTextPaint.setColor(ContextCompat.getColor(context,R.color.filtertextcolor));
                canvas.drawText("" + (int) sum, textXFactor + thumbHalfWidth, top, rightTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int motionEvent = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (motionEvent >= thumb1X - thumbHalfWidth
                        && motionEvent <= thumb1X + thumbHalfWidth) {
                    selectedThumb = 1;
                } else if (motionEvent >= thumb2X - thumbHalfWidth
                        && motionEvent <= thumb2X + thumbHalfWidth) {
                    selectedThumb = 2;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isFirstTime = false;
                if (selectedThumb == 1) {
                    if (motionEvent < thumb2X - 2.7 * thumbHalfWidth)
                        thumb1X = motionEvent;
                } else if (selectedThumb == 2) {
                    if (motionEvent > thumb1X + 2.7 * thumbHalfWidth)
                        thumb2X = motionEvent;
                }
                break;
            case MotionEvent.ACTION_UP:
                selectedThumb = 0;
                break;
        }

        if (thumb1X < minimumRangePosition - thumbBitmap.getHeight() / 2)
            thumb1X = minimumRangePosition - thumbBitmap.getHeight() / 2;

        if (thumb2X >= maximumRangePosition)
            thumb2X = maximumRangePosition;

        invalidate();
        if (seekBarChangeListener != null) {
            calculateThumbValue();
            seekBarChangeListener.SeekBarValueChanged(Integer.parseInt(thumbOneValue), Integer.parseInt(thumbTwoValue), isCheck, thumb1X, thumb2X);
            Constant.filterFlag=true;
        }
        return true;
    }

    private void calculateThumbValue() {


        if (selectedThumb == 1) {
            double per1 = pixelsPerSlot / range;

            int value = (int) ((thumb1X - 27) / per1) + min;
            if (value > max) {
                value = max;
            }
            if (value < min) {
                value = min;
            }
            thumbOneValue = Integer.toString(value);

        }
        if (selectedThumb == 2) {
            double per1 = pixelsPerSlot / range;
            int value = max - (int) ((maximumRangePosition - thumb2X) / per1);
            if (value < min) {
                value = min;
            }
            if (value > max) {
                value = max;
            }
            thumbTwoValue = Integer.toString(value);
        }

    }



    public interface SeekBarChangeListener {
        void SeekBarValueChanged(int minValue, int maxValue, boolean isCheck, int minSelectedPos, int maxSelecredPos);

    }

    private void initView(Canvas canvas) {
        canvas.drawBitmap(thumbBitmap, xFactor, minimumRangePosition - thumbBitmap.getHeight() / 2, paint);
        canvas.drawBitmap(thumbBitmap, xFactor, maximumRangePosition - thumbBitmap.getHeight() / 2, paint);
        if (thumbOneValue != null && thumbTwoValue != null) {
            if (language.equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)) {
                canvas.drawText(thumbOneValue, thumbBitmap.getWidth() / 2, minimumRangePosition + thumbBitmap.getHeight() / 4, leftTextPaint);
                canvas.drawText(thumbTwoValue, thumbBitmap.getWidth() / 2, maximumRangePosition + thumbBitmap.getHeight() / 4, leftTextPaint);
            } else {
                canvas.drawText(thumbOneValue, (int) (0.65 * getWidth()) + thumbHalfWidth, minimumRangePosition + thumbBitmap.getHeight() / 4, leftTextPaint);
                canvas.drawText(thumbTwoValue, (int) (0.65 * getWidth()) + thumbHalfWidth, maximumRangePosition + thumbBitmap.getHeight() / 4, leftTextPaint);
            }
        }
        invalidate();
    }
}
