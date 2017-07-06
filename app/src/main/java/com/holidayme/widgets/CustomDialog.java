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

public class CustomDialog extends Dialog implements OnClickListener {

    private DialogExitListener dialogExitListener;
    String title, message,button_txt;
    Context mContext;

    public CustomDialog(Context context, String title, String message, String button_txt) {
        super(context);

        this.title = title;
        this.message = message;
        this.button_txt=button_txt;
        this.mContext=context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdailog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        getWindow().setAttributes(lp);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCanceledOnTouchOutside(false);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        showDialog();
        initComponent();

    }

    @Override
    public void cancel() {
       // dialogExitListner.dialogExitWithDismissOrCancel();
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
        ((Button)findViewById(R.id.cancelButton)).setText(button_txt);
    }

    public  interface DialogExitListener {
        void dialogExitWithDone();
        void dialogExitWithDismissOrCancel();
    }

}
