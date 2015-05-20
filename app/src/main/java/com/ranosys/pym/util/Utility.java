package com.ranosys.pym.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ranosys.pym.R;


public class Utility {

	private static final String TAG = "PYM";

	private static ProgressDialog progressDialog;

	/**
	 * To hide the soft key pad if open
	 * 
	 */
	public static void hideSoftKeypad(Context context) {
		Activity activity = (Activity) context;
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (activity.getCurrentFocus() != null) {
			imm.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), 0);
		}
	}

	/**
	 * To hide the soft key pad if open
	 *
	 */
	public static void hideSoftKeypadFromDialog(Context context, AlertDialog dialog) {
		Activity activity = (Activity) context;
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (dialog.getWindow() != null) {
			imm.hideSoftInputFromWindow(dialog.getWindow().getCurrentFocus().getWindowToken(), 0);
		}
	}

	public static ProgressDialog getProgressDialog(final Context context, boolean cancelable) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setCanceledOnTouchOutside(cancelable);
		progressDialog.setCancelable(cancelable);
		progressDialog.setMessage(context.getString(R.string.progress_loding_message));
		return progressDialog;
	}

	public static void showProgressDialog(final Context context, boolean cancelable) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setCanceledOnTouchOutside(cancelable);
		progressDialog.setCancelable(cancelable);
		progressDialog.setMessage(context.getString(R.string.progress_loding_message));
		progressDialog.show();
	}

	public static void dismissProgressDialog(){
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public static void handelException(Exception e){
		e.printStackTrace();
	}

	public static void showToast(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, int messageId){
		Toast.makeText(context, context.getString(messageId), Toast.LENGTH_LONG).show();
	}
}
