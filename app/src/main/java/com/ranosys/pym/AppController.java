package com.ranosys.pym;

import android.app.Application;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.facebook.FacebookSdk;
import com.ranosys.pym.preference.PYMPreference;

/**
 * Created by ranosys-sid on 13/5/15.
 */
public class AppController extends Application {

    private static AppController appController;
    private AQuery aQuery;
    private BaseActivity currentActivity;
    /**
     * Get instance of Application class
     * @return
     */
    public static AppController getInstance(){
        return appController;
    }

    /**
     * Get instance of Aquery
     * @return
     */
    public AQuery getAquery(){
        return aQuery;
    }


    public BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(BaseActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appController = this;
        AjaxCallback.setNetworkLimit(8);
        aQuery = new AQuery(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        PYMPreference.init(getApplicationContext());
    }


}
