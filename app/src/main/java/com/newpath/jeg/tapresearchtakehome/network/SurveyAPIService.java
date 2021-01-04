package com.newpath.jeg.tapresearchtakehome.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newpath.jeg.tapresearchtakehome.disk.SurveyLocalStored;
import com.newpath.jeg.tapresearchtakehome.interaces.SurveyCallback;
import com.newpath.jeg.tapresearchtakehome.models.SurveyDataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.newpath.jeg.tapresearchtakehome.utils.networkUtils.networkConnected;

/**
 * Survey related network APIs
 */
public class SurveyAPIService {

    private static SurveyAPIService sSurveyService;
    RequestQueue mRequestQueue;
    private Context context;
    public static final String TAG = "SurveyAPIService";

    SurveyAPIService(Context appContext){
        mRequestQueue = Volley.newRequestQueue(appContext);
        this.context = appContext;
    }

    public static SurveyAPIService getInstance(Context appContext){
        if(sSurveyService == null){
            sSurveyService = new SurveyAPIService(appContext);
        }
        return sSurveyService;
    }

    public void requestSurvey(final SurveyCallback callback){

        String URL = "https://www.tapresearch.com/supply_api/surveys/offer"; //can externalize if needed
        String apiToken = "f47e5ce81688efee79df771e9f9e9994";
        String deviceIdent = "GAID";
        String userIdent = "codetest123";

        try {
            Thread.sleep(3000); //adding delay to better showcase that expiration policy is working properly
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!networkConnected(context)){
            callback.onFailure("Network Unavailable");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                SurveyDataModel survey;

                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String offerUrl = jsonObject.getString("offer_url");
                    String abandonUrl = jsonObject.getString("abandon_url");
                    Boolean hasOffer = jsonObject.getBoolean("has_offer");
                    survey = new SurveyDataModel(offerUrl, abandonUrl, hasOffer, System.currentTimeMillis());
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onFailure("Invalid JSON format");
                    return;
                }

                callback.onSuccess(survey);

                //save in local storage
                SurveyLocalStored.saveStoredOffer(context, survey); //could use DI instead, or save it in the repo class

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.getLocalizedMessage());
                try {
                    Log.e(TAG, error.getMessage().toString());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }){
            /*
                Set the post request parameters
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<>();
                parameters.put("device_identifier", deviceIdent);
                parameters.put("api_token",apiToken);
                parameters.put("user_identifier",userIdent);
                return parameters;
            }

        };

        // Adding request to request queue
        mRequestQueue.add(stringRequest);

    }


}
