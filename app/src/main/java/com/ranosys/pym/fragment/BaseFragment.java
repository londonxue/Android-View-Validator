package com.ranosys.pym.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranosys.pym.BaseActivity;
import com.ranosys.pym.HomeActivity;
import com.ranosys.pym.LoginSignUpActivity;
import com.ranosys.pym.model.UserModel;
import com.ranosys.pym.preference.PYMPreference;

/**
 * Created by ranosys-sid on 12/5/15.
 */
abstract public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Kind of abstract method, the sub class can override this method when using login with social media.
     * As this method given the user detail on successful login
     * @param user
     */
    public void onSocialLoginSuccess(UserModel user){}

    /**
     * Kind of abstract method, the sub class can override this method when using login with FB or any other social media.
     * As this method will notify that login with that media failed
     */
    public void onSocialLoginFailedOrCanceled(String message){}

    public void doFBLogin(){
        if(getActivity() instanceof LoginSignUpActivity){
            ((LoginSignUpActivity)getActivity()).doFacebookLogin();
        }
    }

    public void doFBLogout(){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).doFacebookLogout();
        }
    }

    public void loginUser(UserModel userModel){
        if(userModel == null || userModel.getAuthKey() == null){
            return;
        }
        PYMPreference preference = PYMPreference.getInstance();
        preference.saveStringValue(PYMPreference.AUTH_KEY, userModel.getAuthKey());
        Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
    }

    public void addFragment(BaseFragment fragment){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).addFragment(fragment);
        }
    }

    abstract public int getTitle();

    @Override
    public void onResume() {
        super.onResume();
        int titleId = getTitle();
        if(titleId >0){
            getActivity().setTitle(titleId);
        }
    }
}
