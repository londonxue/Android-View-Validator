package com.ranosys.pym.validation;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ranosys.pym.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ranosys-sid on 13/5/15.
 */
public class ViewValidator {

    private boolean isValid = true;
    private String password;
    public boolean isValid(View view){
        if(view == null){
            return false;
        }

        if(view instanceof ViewGroup){
            int childCount = ((ViewGroup)view).getChildCount();
            for(int i =0;i<childCount;i++){
                View childView = ((ViewGroup)view).getChildAt(i);
                if(childView instanceof ViewGroup){
                    isValid = isValid(childView);
                }else{
                    validateView(childView);
                }
            }
        }else{
            validateView(view);
        }
        return isValid;
    }

    private void validateView(View view){
        if(view instanceof EditText){
            EditText et = (EditText)view;
            switch (et.getId()){
                case R.id.name:
                        nameValidation(et);
                    break;
                case R.id.email:
                        emailValidator(et);
                    break;
                case R.id.password:
                        passwordValidation(et);
                        password = et.getText().toString();
                    break;
                case R.id.confirm_password:
                        confirmPasswordValidation(et, password);
                    break;
            }
        }
    }

    private void nameValidation(EditText inputET) {
        String value = inputET.getText().toString();
        if (TextUtils.isEmpty(value)) {
            inputET.setError(inputET.getContext().getString(R.string.error_field_required));
            isValid = false;
        } else {
            String PATTERN_MATCHER = "(^[a-zA-Z ]{1,50}+$)";
            Pattern pattern = Pattern.compile(PATTERN_MATCHER);
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                inputET.setError(inputET.getContext().getString(R.string.error_invalid_name));
                isValid = false;
            }

        }
    }


    private void passwordValidation(EditText inputET) {
        String value = inputET.getText().toString();
        if (TextUtils.isEmpty(value)) {
            inputET.setError(inputET.getContext().getString(R.string.error_field_required));
            isValid = false;
        } else {
            String PATTERN_MATCHER = "(^.{8,50}+$)";
            Pattern pattern = Pattern.compile(PATTERN_MATCHER);
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                inputET.setError(inputET.getContext().getString(R.string.error_password_length));
                isValid = false;
            }
        }
    }

    private void emailValidator(EditText inputET) {
        String value = inputET.getText().toString();
        if (TextUtils.isEmpty(value)) {

            inputET.setError(inputET.getContext().getString(R.string.error_field_required));
            isValid = false;

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                inputET.setError(inputET.getContext().getString(R.string.error_invalid_email));
                isValid = false;
        }
    }

    private void phoneNumberRequiredValidation(EditText inputET) {
        String value = inputET.getText().toString();
        if (TextUtils.isEmpty(value)) {
            inputET.setError(inputET.getContext().getString(R.string.error_field_required));
            isValid = false;
        } else {
            String PATTERN_MATCHER = "(^[0-9]{8,20}+$)";
            Pattern pattern = Pattern.compile(PATTERN_MATCHER);
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                inputET.setError(inputET.getContext().getString(R.string.error_invalid_number));
                isValid = false;
            }

        }
    }

    private void confirmPasswordValidation(EditText confirmPasswordET, String alreadySetPassword){
        String value = confirmPasswordET.getText().toString();
        if (TextUtils.isEmpty(value)) {
            confirmPasswordET.setError(confirmPasswordET.getContext().getString(R.string.error_field_required));
            isValid = false;
        }else if(!TextUtils.isEmpty(alreadySetPassword) && !alreadySetPassword.equals(value)){
            confirmPasswordET.setError(confirmPasswordET.getContext().getString(R.string.error_incorrect_password));
            isValid = false;
        }
    }

}
