package com.ranosys.pym;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ranosys.pym.dialog.DialogFactory;
import com.ranosys.pym.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ranosys-sid on 13/5/15.
 */
public class APIController {

    private Context context;
    public static final String BASE_URL = "http://ranosys.net/client/pym/api/web/v1/";

    public APIController() {
        this.context = AppController.getInstance().getCurrentActivity();
    }

    /**
     * Method to call API by POST or GET.
     * 1) For Post pass parameter in a HashMap
     * 2) For Get pass null for parameter and instead add them in url
     * @param url
     * @param params
     * @param handler
     */
    public void callAPI(String url, HashMap<String, Object> params, final APIHandler<JSONObject> handler){
        AQuery aQuery = AppController.getInstance().getAquery();
        Utility.hideSoftKeypad(context);
        aQuery.progress(Utility.getProgressDialog(context, false))
                .ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        super.callback(url, object, status);

                        switch (status.getCode()) {
                            case -101:
                                DialogFactory.showMessageDialog(R.string.error_network);
                                break;
                            default:
                                if (object != null) {
                                    try {
                                        if (object.getInt("status") == 200) {
                                            handler.onSuccess(object);
                                        } else {
                                            handler.onError(object.getString("message"));
                                        }
                                    } catch (JSONException e) {
                                        Utility.handelException(e);
                                        DialogFactory.showMessageDialog(R.string.error_response);
                                    }
                                } else if(status != null && status.getError() != null){
                                    try {
                                        JSONObject errorObj = new JSONObject(status.getError());
                                        handler.onError(errorObj.getString("message"));
                                    } catch (JSONException e) {
                                        Utility.handelException(e);
                                        DialogFactory.showMessageDialog(R.string.error_response);
                                    }
                                }else {
                                    DialogFactory.showMessageDialog(R.string.error_response);
                                }
                                break;
                        }
                    }
                });
    }

    /**
     * Interface having callbacks which will be called on an API calling
     * @param <T>
     */
    public interface APIHandler<T>{
        public void onSuccess(T response);
        public void onError(String message);
    }
}
