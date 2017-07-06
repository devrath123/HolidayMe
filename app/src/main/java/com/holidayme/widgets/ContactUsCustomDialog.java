package com.holidayme.widgets;

/**
 * Created by abhishek.deshpande on 5/5/2016.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.data.UserDTO;


public class ContactUsCustomDialog extends Dialog implements View.OnClickListener {


    private DialogExitListener dialogExitListener;
    private TextView callButton;

    private String phoneNumber,countryCode;
    private Context mContext;


    public ContactUsCustomDialog(Context context, String countryCode) {
        super(context);

        this.mContext=context;
        this.countryCode=countryCode;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contactusdailogue);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCanceledOnTouchOutside(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        showDialog();
        initComponent();

    }

    @Override
    public void cancel() {

        dialogExitListener.dialogExitWithDismissOrCancel();
        super.cancel();
    }

    public void setDialogExitListner(DialogExitListener dialogExitListner) {
        this.dialogExitListener = dialogExitListner;
        dialogExitListner.dialogExitWithDismissOrCancel();
        super.cancel();
    }


    private void initComponent() {

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        TextView call1Button = (TextView) findViewById(R.id.dialog_call1);
         callButton = (TextView) findViewById(R.id.dialog_call2);

        switch (countryCode) {
            case "AE":
                phoneNumber = getContext().getString(R.string.uae_toll_free_number);
                callButton.setText(getContext().getString(R.string.uae_toll_free_text));

                break;
            case "SA":
                phoneNumber = getContext().getString(R.string.saudi_toll_free_number);
                callButton.setText(getContext().getString(R.string.saudi_toll_free_text));
                break;
            default:
                phoneNumber = getContext().getString(R.string.worldwide_toll_free_number);
                callButton.setText(getContext().getString(R.string.worldwide_toll_free_text)+" "+getContext().getString(R.string.worldwide_toll_free_number));
                break;
        }
        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)){
            HolidayMeFont.overrideFonts(mContext, cancelButton, Constant.NotoKufiArabic_Bold);
        }else{
            HolidayMeFont.overrideFonts(mContext, cancelButton, Constant.HelveticaNeueRoman);
        }


        cancelButton.setOnClickListener(this);
        call1Button.setOnClickListener(this);
        callButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cancelButton) {
            dismiss();
            dialogExitListener.dialogExitWithDismissOrCancel();
        } else if (v.getId() == R.id.doneButton) {
            dialogExitListener.dialogExitWithDone();

            dialogExitListener.dialogExitWithDismissOrCancel();
        } else if (v.getId() == R.id.doneButton) {
            dialogExitListener.dialogExitWithDone();
            dismiss();
        }
        else if (v.getId() == R.id.dialog_call1) {
            dismiss();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+(971) 44298989"));
            getContext().startActivity(intent);
        }
        else if (v.getId() == R.id.dialog_call2) {
            dismiss();
            String phone = phoneNumber;
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            getContext().startActivity(intent);
        }
    }


    private void showDialog() {

    }

    public interface DialogExitListener {
        void dialogExitWithDone();

        void dialogExitWithDismissOrCancel();
    }


    public interface DialogExitListner {
        void dialogExitWithDone();

        void dialogExitWithDismisOrCancel();
    }

}
