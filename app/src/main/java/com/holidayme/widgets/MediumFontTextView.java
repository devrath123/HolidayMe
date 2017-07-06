package com.holidayme.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.data.UserDTO;

import java.util.HashMap;

/**
 * Created by supriya.sakore on 06-04-2016.
 */
public class MediumFontTextView extends TextView{
    public MediumFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }
    public MediumFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }
    public MediumFontTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }


    private void applyCustomFont(Context context) {
        Typeface customFont;

            if (UserDTO.getUserDTO() != null && UserDTO.getUserDTO().getLanguage()!=null && UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                customFont = LightFontTextView.FontCache.getTypeface("NotoKufiArabic-Bold.ttf", context);
            else
                customFont = FontCache.getTypeface("HelveticaNeueLTStd-Roman.ttf", context);
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
