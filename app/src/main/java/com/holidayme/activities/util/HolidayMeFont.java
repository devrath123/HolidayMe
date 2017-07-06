package com.holidayme.activities.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by santosh.patar on 07-09-2015.
 */
public class HolidayMeFont {// how to use
    //BOLDFONT

    //Tglutil.overrideFonts(mContext, holder.cuisinetab_relative_layout, Constant.BOLDFONT);

    public static void overrideFonts(final Context context, final View v,String Font) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child,Font);
                }
            } else if (v instanceof EditText) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(
                        context.getAssets(), Font));
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(
                        context.getAssets(), Font));
            } else if (v instanceof Button) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(
                        context.getAssets(), Font));
            }else if(v instanceof RadioButton){
                ((TextView) v).setTypeface(Typeface.createFromAsset(
                        context.getAssets(), Font));
            }
        } catch (Exception e) {
        }
    }

}
