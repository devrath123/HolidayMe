package com.holidayme.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.data.UserDTO;

public class CustomeDailogWithTwoButtons extends Dialog implements OnClickListener {

    private DialogExitListener dialogExitListener;
    String title, message, button1_txt, button2_txt;
    Context mContext;

    public CustomeDailogWithTwoButtons(Context context, String title, String message, String button_no_txt, String button_yes_txt) {
        super(context);

        this.title = title;
        this.message = message;
        this.button1_txt = button_no_txt;
        this.button2_txt = button_yes_txt;
        this.mContext=context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdailog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCanceledOnTouchOutside(false);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        showDialog();
        initComponent();

    }

    @Override
    public void cancel() {
        dialogExitListener.dialogExitWithDismissOrCancel();
        super.cancel();
    }

    public void setDialogExitListener(DialogExitListener dialogExitListener) {
        this.dialogExitListener = dialogExitListener;
    }

    private void initComponent() {

        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(this);

        Button doneButton = (Button) findViewById(R.id.doneButton);

        doneButton.setOnClickListener(this);
        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)){
            HolidayMeFont.overrideFonts(mContext, doneButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(mContext, cancelButton, Constant.NotoKufiArabic_Bold);
        }else{
            HolidayMeFont.overrideFonts(mContext, doneButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(mContext, cancelButton, Constant.HelveticaNeueRoman);
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cancelButton) {
            dismiss();
            dialogExitListener.dialogExitWithDismissOrCancel();
        } else if (v.getId() == R.id.doneButton) {
            dialogExitListener.dialogExitWithDone();
            dismiss();

        }
    }


    private void showDialog() {

        ((TextView) findViewById(R.id.dialog_title)).setText(title);
        ((TextView) findViewById(R.id.dialog_msg)).setText(message);
        ((Button) findViewById(R.id.cancelButton)).setText(button1_txt);
        findViewById(R.id.doneButton).setVisibility(View.VISIBLE);
        findViewById(R.id.divider_line).setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.doneButton)).setText(button2_txt);
    }

    public interface DialogExitListener {
        void dialogExitWithDone();
        void dialogExitWithDismissOrCancel();
    }


}
