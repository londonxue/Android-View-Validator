package com.ranosys.pym;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.ranosys.pym.fragment.LoginFragment;
import com.ranosys.pym.model.UserModel;
import com.ranosys.pym.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginSignUpActivity extends BaseActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(new LoginFragment());
    }

    /**
     * Method to do facebook login
     * This method calls two callbacks based on success or failure, @onSocialLoginSuccess and onSocialLoginFailedOrCanceled
     */
    public void doFacebookLogin(){
        setFbCallbackManager();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    /**
     * Set FB callback manager. it is important when doing any FB operation
     */
    private void setFbCallbackManager(){
        if(callbackManager == null) {
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager, facebookLoginCallback);
        }
    }

    /**
     * Kind of abstract method, the sub class can override this method when using login with social media.
     * As this method given the user detail on successful login
     * @param user
     */
    public void onSocialLoginSuccess(UserModel user){
        if(getCurrentFragment() != null){
            getCurrentFragment().onSocialLoginSuccess(user);
        }
    }

    /**
     * Kind of abstract method, the sub class can override this method when using login with social media.
     * As this method will notify that login with that media failed
     */
    public void onSocialLoginFailedOrCanceled(String message){
        if(getCurrentFragment() != null){
            getCurrentFragment().onSocialLoginFailedOrCanceled(message);
        }
    }

    /**
     * Facebook login callback
     */
    private FacebookCallback<LoginResult> facebookLoginCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            getUserDataFromFb(loginResult.getAccessToken());
        }

        @Override
        public void onCancel() {

            onSocialLoginFailedOrCanceled(null);
        }

        @Override
        public void onError(FacebookException e) {
            onSocialLoginFailedOrCanceled(getString(R.string.error_login_with_fb));
        }
    };

    /**
     * This method is called on facebook success login, It call graph API to get user data from FB.
     * Then parse that data into a UserModel call and the call @onSocialLoginSuccess so to let UI get the required response
     * @param accessToken
     */
    private void getUserDataFromFb(AccessToken accessToken){
        Utility.showProgressDialog(this, false);
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Utility.dismissProgressDialog();
                        if(object != null) {
                            try {
                                Log.d("Fb Me response: ", object.toString());
                                UserModel userModel = new UserModel();
                                userModel.setEmail(object.getString("email"));
                                userModel.setUniqueId(object.getString("email"));
                                userModel.setName(object.getString("name"));
                                userModel.setUserFBId(object.getString("id"));
                                userModel.setUserFBAccountLink(object.getString("link"));
                                onSocialLoginSuccess(userModel);
                            }catch (JSONException e){
                                Utility.handelException(e);
                            }
                        }else{
                            onSocialLoginFailedOrCanceled(getString(R.string.error_getting_user_detail_from_fb));
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(callbackManager != null){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
