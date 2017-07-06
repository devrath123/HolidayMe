/*
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
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.data.UserDTO;

public class BookingFailCustomDialog extends Dialog implements OnClickListener {

    private DialogExitListener dialogExitListener;
    private Context context;

    public BookingFailCustomDialog(Context context) {
        super(context);
        this.context =context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.booking_fail_customdailog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        showDialog();
        initComponent();

    }

    @Override
    public void cancel() {
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
            HolidayMeFont.overrideFonts(context, doneButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(context, cancelButton, Constant.NotoKufiArabic_Bold);
        }else{
            HolidayMeFont.overrideFonts(context, doneButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(context, cancelButton, Constant.HelveticaNeueRoman);
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
    }

    public interface DialogExitListener {
        void dialogExitWithDone();
        void dialogExitWithDismissOrCancel();
    }


}
*/
