package com.newpath.jeg.tapresearchtakehome.repo;

import android.content.Context;
import android.util.Log;

import com.newpath.jeg.tapresearchtakehome.disk.SurveyLocalStored;
import com.newpath.jeg.tapresearchtakehome.interaces.SurveyCallback;
import com.newpath.jeg.tapresearchtakehome.models.SurveyDataModel;
import com.newpath.jeg.tapresearchtakehome.network.SurveyAPIService;
import com.newpath.jeg.tapresearchtakehome.ui.SurveyOffer.SurveyOfferFragment;

/**
 * Survey repository, fetched survey from local disk if conditions are met, else it fallsback on network.
 */
public class SurveyRepo {

    public static final String TAG = "SurveyRepo";
    private static final Long expirationTime = 30000L;  //survey expiration 30s in millis

    public static void getSurvey(Context appContext, final SurveyCallback callback){

        SurveyDataModel surveyFetched = SurveyLocalStored.loadStoredOffer(appContext); //try loading from local storage first

        if (surveyFetched == null || surveyFetched.getTimeFetched() + expirationTime < System.currentTimeMillis() ){
            Log.i(TAG, "Survey is null or expired, fetching from network");
            SurveyAPIService.getInstance(appContext).requestSurvey(callback);
        } else {
            Log.i(TAG,"fetched survey from local disk");
            callback.onSuccess(surveyFetched);
        }
    }



}
