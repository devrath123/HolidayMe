package com.holidayme.common;


import android.app.Dialog;
import android.content.Context;

import com.holidayme.activities.R;

public class CustomProgressDialog {



	public static Dialog showProgressDialog(Context ctx) {

		Dialog	dialog = new Dialog(ctx, R.style.Progres_Custom_Dialog);
		
		dialog.setContentView(R.layout.progress_dilog_layout);
		
		dialog.setCancelable(false);
		

		return dialog;
	}

}
