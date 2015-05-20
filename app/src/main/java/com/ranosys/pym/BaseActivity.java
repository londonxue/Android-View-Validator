package com.ranosys.pym;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.facebook.login.LoginManager;
import com.ranosys.pym.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by ranosys-sid on 11/5/15.
 */
public class BaseActivity extends AppCompatActivity {

    private BaseFragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppController.getInstance().setCurrentActivity(this);
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppController.getInstance().setCurrentActivity(this);
    }

    /**
     * Method to be used when sub class has set his own layout and wants to add the given fragment to the passed container
     * @param containerId
     * @param fragment
     */
    public void addFragment(int containerId, BaseFragment fragment){
        if(fragment == null || containerId <=0)
            return;
        currentFragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.popup_enter, R.anim.popup_exit);
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    /**
     * Method to be used when you want to add fragment in the base layout container.
     * use when your screen just have a single fragment
     * @param fragment
     */
    public void addFragment(BaseFragment fragment){
        addFragment(R.id.fragment_container, fragment);
    }

    public void doFacebookLogout(){
        LoginManager.getInstance().logOut();
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public void onBackPressed() {
        handelBackPress();
    }

    private void handelBackPress(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() == 1){
            finish();
        }else{
            super.onBackPressed();
        }
    }
}
