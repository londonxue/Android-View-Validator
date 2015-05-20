package com.ranosys.pym.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ranosys.pym.BaseActivity;
import com.ranosys.pym.R;
import com.ranosys.pym.model.RegisterMedium;
import com.ranosys.pym.model.UserModel;
import com.ranosys.pym.util.Utility;
import com.ranosys.pym.validation.ViewValidator;

/**
 * Created by ranosys-sid on 12/5/15.
 */
public class RegisterFragment extends BaseFragment {

    private EditText nameET, emailET, passwordET, confirmPasswordET;
    private Button registerBtn;
    private UserModel userData;
    private LinearLayout rootLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, null);
        nameET = (EditText) view.findViewById(R.id.name);
        emailET = (EditText) view.findViewById(R.id.email);
        passwordET = (EditText) view.findViewById(R.id.password);
        confirmPasswordET = (EditText) view.findViewById(R.id.confirm_password);
        rootLayout = (LinearLayout) view.findViewById(R.id.register_form);
        registerBtn = (Button) view.findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new ViewValidator().isValid(rootLayout)){
                    setData();
                    userData.register(RegisterMedium.Direct);
                }
            }
        });

        return view;
    }

    private void setData(){
        userData = new UserModel();
        userData.setName(nameET.getText().toString().trim());
        userData.setEmail(emailET.getText().toString().trim());
        userData.setUniqueId(emailET.getText().toString().trim());
        userData.setPassword(passwordET.getText().toString().trim());
    }

    @Override
    public int getTitle() {
        return R.string.register_title;
    }
}
