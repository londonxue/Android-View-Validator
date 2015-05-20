package com.ranosys.pym.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranosys.pym.BaseActivity;
import com.ranosys.pym.R;
import com.ranosys.pym.dialog.DialogFactory;
import com.ranosys.pym.model.RegisterMedium;
import com.ranosys.pym.model.UserModel;
import com.ranosys.pym.util.Utility;
import com.ranosys.pym.validation.ViewValidator;

/**
 * Created by ranosys-sid on 12/5/15.
 */
public class LoginFragment extends BaseFragment {

    private EditText emailET, passwordET;
    private TextView forgotPasswordTV;
    private UserModel userData;
    private LinearLayout rootLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);

        emailET = (EditText) view.findViewById(R.id.email);
        passwordET = (EditText) view.findViewById(R.id.password);
        forgotPasswordTV = (TextView) view.findViewById(R.id.forgot_password);
        rootLayout = (LinearLayout) view.findViewById(R.id.root_layout);

        Button loginBtn = (Button) view.findViewById(R.id.email_sign_in_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new ViewValidator().isValid(rootLayout)){
                    setData();
                    userData.login();
                }
            }
        });

        Button loginWithFbBtn = (Button) view.findViewById(R.id.fb_sign_in_button);
        loginWithFbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doFBLogin();
            }
        });

        Button registerBtn = (Button) view.findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new RegisterFragment());
            }
        });

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFactory.showForgotPasswordDialog();
            }
        });

        return view;
    }

    @Override
    public void onSocialLoginSuccess(UserModel user) {
        user.register(RegisterMedium.Facebook);
    }

    @Override
    public void onSocialLoginFailedOrCanceled(String message) {
        Utility.showToast(getActivity(), message);
    }

    private void setData(){
        userData = new UserModel();
        userData.setEmail(emailET.getText().toString().trim());
        userData.setPassword(passwordET.getText().toString().trim());
    }

    @Override
    public int getTitle() {
        return R.string.login_title;
    }

}
