package com.holidayme.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.holidayme.Constants.Constant;
import com.holidayme.data.UserDTO;

import java.util.HashMap;

/**
 * Created by supriya.sakore on 11-08-2016.
 */

public class LightFontEditText extends EditText {
    public LightFontEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public LightFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public LightFontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont;
        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
            customFont = LightFontTextView.FontCache.getTypeface("NotoKufiArabic-Regular.ttf", context);
        else
           customFont = LightFontEditText.FontCache.getTypeface("HelveticaNeueLTStd-Lt.ttf", context);
        setTypeface(customFont);
    }


    public static class FontCache {

        private static HashMap<String, Typeface> fontCache = new HashMap<>();

        public static Typeface getTypeface(String fontname, Context context) {
            Typeface typeface = fontCache.get(fontname);

            if (typeface == null) {
                try {
                    typeface = Typeface.createFromAsset(context.getAssets(), fontname);
                } catch (Exception e) {
                    return null;
                }

                fontCache.put(fontname, typeface);
            }

            return typeface;
        }
    }
}
