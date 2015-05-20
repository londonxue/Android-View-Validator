package com.ranosys.pym.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.ranosys.pym.AppController;
import com.ranosys.pym.BaseActivity;
import com.ranosys.pym.R;
import com.ranosys.pym.model.UserModel;
import com.ranosys.pym.util.Utility;
import com.ranosys.pym.validation.ViewValidator;

/**
 * Created by ranosys-sid on 14/5/15.
 */
public class DialogFactory {

    private DialogPositiveButtonClickListener dialogPositiveButtonClickListener;
    private DialogNegativeButtonClickListener dialogNegativeButtonClickListener;

    public static void showMessageDialog(String message){
        BaseActivity baseActivity = AppController.getInstance().getCurrentActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setMessage(message);
        builder.setPositiveButton(baseActivity.getString(R.string.ok_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    public static void showMessageDialog(int message){
        BaseActivity baseActivity = AppController.getInstance().getCurrentActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setMessage(message);
        builder.setPositiveButton(baseActivity.getString(R.string.ok_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    public static void showMessageDialogWithListener(String message,
                                                     final DialogPositiveButtonClickListener dialogPositiveButtonClickListener,
                                                     final DialogNegativeButtonClickListener dialogNegativeButtonClickListener){
        BaseActivity baseActivity = AppController.getInstance().getCurrentActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(baseActivity.getString(R.string.ok_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogPositiveButtonClickListener != null) {
                    dialogPositiveButtonClickListener.onPositiveClick(dialog);
                } else {
                    dialog.dismiss();
                }
            }
        });
        if(dialogNegativeButtonClickListener != null){
            builder.setNegativeButton(baseActivity.getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogNegativeButtonClickListener.onNegativeClick(dialog);
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    public static void showMessageDialogWithListener(int message,
                                                 final DialogPositiveButtonClickListener dialogPositiveButtonClickListener,
                                                 final DialogNegativeButtonClickListener dialogNegativeButtonClickListener){
        BaseActivity baseActivity = AppController.getInstance().getCurrentActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(baseActivity.getString(R.string.ok_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogPositiveButtonClickListener != null) {
                    dialogPositiveButtonClickListener.onPositiveClick(dialog);
                } else {
                    dialog.dismiss();
                }
            }
        });
        if(dialogNegativeButtonClickListener != null){
            builder.setNegativeButton(baseActivity.getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogNegativeButtonClickListener.onNegativeClick(dialog);
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    public static void showForgotPasswordDialog(){
        final BaseActivity baseActivity = AppController.getInstance().getCurrentActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setTitle(R.string.forgot_password);
        View view = LayoutInflater.from(baseActivity).inflate(R.layout.edit_text_view, null);
        final EditText email = (EditText) view.findViewById(R.id.email);
        builder.setView(view);
        builder.setPositiveButton(R.string.yes_button, null);
        builder.setNegativeButton(R.string.no_button, null);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new ViewValidator().isValid(email)) {
                    Utility.hideSoftKeypadFromDialog(baseActivity, dialog);
                    dialog.dismiss();
                    UserModel user = new UserModel();
                    user.setEmail(email.getText().toString().trim());
                    user.forgotPassword();
                }
            }
        });
    }

    public void setDialogPositiveButtonClickListener(DialogPositiveButtonClickListener dialogPositiveButtonClickListener) {
        this.dialogPositiveButtonClickListener = dialogPositiveButtonClickListener;
    }

    public void setDialogNegativeButtonClickListener(DialogNegativeButtonClickListener dialogNegativeButtonClickListener) {
        this.dialogNegativeButtonClickListener = dialogNegativeButtonClickListener;
    }

    public interface DialogPositiveButtonClickListener{
        public void onPositiveClick(DialogInterface dialog);
    }

    public interface DialogNegativeButtonClickListener{
        public void onNegativeClick(DialogInterface dialog);
    }
}
