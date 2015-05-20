package com.ranosys.pym;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;

import com.ranosys.pym.preference.PYMPreference;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
            delay((long) getResources().getInteger(R.integer.splash_delay));
    }

    private void delay(Long timeInMilli){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isFinishing()){
                    start();
                }
            }
        }, timeInMilli);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                start();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void start(){
        PYMPreference preference = PYMPreference.getInstance();
        if(TextUtils.isEmpty(preference.getStringValue(PYMPreference.AUTH_KEY))){
            Intent loginIntent = new Intent(this, LoginSignUpActivity.class);
            startActivity(loginIntent);
        } else {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        }
        finish();
    }
}
