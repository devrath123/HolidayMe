package com.holidayme.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.holidayme.activities.R;

public class RateUsCustomeDailog extends Dialog implements OnClickListener {

    private DialogExitListner dialogExitListner;

    public RateUsCustomeDailog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
          setContentView(R.layout.rateus_dailogue);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCanceledOnTouchOutside(false);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initComponent();
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    public void setDialogExitListner(DialogExitListner dialogExitListner) {
        this.dialogExitListner = dialogExitListner;
    }

    private void initComponent() {

        TextView rate_us_now = (TextView) findViewById(R.id.txt_rate_now);

        rate_us_now.setOnClickListener(this);

        TextView remind_me_later = (TextView) findViewById(R.id.txt_remind_latter);

        remind_me_later.setOnClickListener(this);

        TextView no_thanks = (TextView) findViewById(R.id.txt_not_now);

        no_thanks.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.txt_rate_now) {
            dismiss();
            dialogExitListner.dialogExitWithRateus_now();
        } else if (v.getId() == R.id.txt_remind_latter) {
            dialogExitListner.dialogExitWithRemind_me_later();
            dismiss();
        } else if (v.getId() == R.id.txt_not_now) {
            dialogExitListner.dialogExitWithNo_thanks();
            dismiss();
        }
    }

    public interface DialogExitListner {
        void dialogExitWithRateus_now();

        void dialogExitWithRemind_me_later();

        void dialogExitWithNo_thanks();
    }

    public void AllDismiss() {

        dismiss();
    }
}
