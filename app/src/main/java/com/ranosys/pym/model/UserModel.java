package com.ranosys.pym.model;

import android.content.DialogInterface;
import android.text.TextUtils;

import com.ranosys.pym.APIController;
import com.ranosys.pym.AppController;
import com.ranosys.pym.BaseActivity;
import com.ranosys.pym.dialog.DialogFactory;
import com.ranosys.pym.fragment.BaseFragment;
import com.ranosys.pym.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by ranosys-sid on 13/5/15.
 */
public class UserModel {

    private String email;
    private String userId;
    private String userFBId;
    private String userFBAccountLink;
    private String name;
    private String password;
    private String uniqueId;
    private String authKey;
    private RegisterMedium registerMedium;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserFBId() {
        return userFBId;
    }

    public void setUserFBId(String userFBId) {
        this.userFBId = userFBId;
    }

    public String getUserFBAccountLink() {
        return userFBAccountLink;
    }

    public void setUserFBAccountLink(String userFBAccountLink) {
        this.userFBAccountLink = userFBAccountLink;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void register(RegisterMedium registerMedium){
        this.registerMedium = registerMedium;
        register();
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    private void register(){
        APIController apiController = new APIController();
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", getName());
        params.put("email", getEmail());
        params.put("register_medium", registerMedium.toString());
        if(!TextUtils.isEmpty(getPassword())) {
            params.put("password", getPassword());
        }
        if(!TextUtils.isEmpty(getUniqueId())) {
            params.put("unique_id", getUniqueId());
        }
        apiController.callAPI(APIController.BASE_URL + "user", params, new APIController.APIHandler<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                if (registerMedium == RegisterMedium.Direct) {
                    try {
                        DialogFactory.showMessageDialogWithListener(response.getString("message"),
                                new DialogFactory.DialogPositiveButtonClickListener() {
                                    @Override
                                    public void onPositiveClick(DialogInterface dialog) {
                                        dialog.dismiss();
                                        BaseActivity currentActivity = AppController.getInstance().getCurrentActivity();
                                        currentActivity.getSupportFragmentManager().popBackStack();
                                    }
                        }, null);
                    }catch (JSONException e){
                        Utility.handelException(e);
                    }
                } else {
                    BaseActivity currentActivity = AppController.getInstance().getCurrentActivity();
                    if (currentActivity != null && currentActivity.getCurrentFragment() instanceof BaseFragment) {
                        try {
                            setAuthKey(response.getJSONObject("data").getString("auth_key"));
                            currentActivity.getCurrentFragment().loginUser(UserModel.this);
                        }catch (JSONException e){
                            Utility.handelException(e);
                        }
                    }
                }
            }

            @Override
            public void onError(String message) {
                DialogFactory.showMessageDialog(message);
            }
        });
    }

    public void login(){
        APIController apiController = new APIController();
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", getEmail());
        params.put("password", getPassword());
        apiController.callAPI(APIController.BASE_URL + "login", params, new APIController.APIHandler<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                BaseActivity currentActivity = AppController.getInstance().getCurrentActivity();
                if (currentActivity != null && currentActivity.getCurrentFragment() instanceof BaseFragment) {
                    try {
                        setAuthKey(response.getJSONObject("data").getString("auth_key"));
                        currentActivity.getCurrentFragment().loginUser(UserModel.this);
                    }catch (JSONException e){
                        Utility.handelException(e);
                    }
                }
            }

            @Override
            public void onError(String message) {
                DialogFactory.showMessageDialog(message);
            }
        });
    }

    public void forgotPassword(){
        APIController apiController = new APIController();
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", getEmail());
        apiController.callAPI(APIController.BASE_URL + "forgot-password", params, new APIController.APIHandler<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    DialogFactory.showMessageDialog(response.getString("message"));
                }catch (JSONException e){
                    Utility.handelException(e);
                }
            }

            @Override
            public void onError(String message) {
                DialogFactory.showMessageDialog(message);
            }
        });
    }
}
