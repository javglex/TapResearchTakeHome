package com.newpath.jeg.tapresearchtakehome.disk;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.newpath.jeg.tapresearchtakehome.models.SurveyDataModel;

import static android.content.Context.MODE_PRIVATE;

public class SurveyLocalStored {

    public static final String PREF_NAME = "SurveyLocalStoredPrefs";
    public static final String SURVEY_NAME = "SURVEY";

    public static void saveStoredOffer(Context context, SurveyDataModel survey){

        SharedPreferences sharedPreferences
                = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

        //serialize our survey object
        Gson gson = new Gson();
        String json = gson.toJson(survey);

        //store it into our prefs
        myEdit.putString(SURVEY_NAME, json);
        myEdit.commit();
    }

    public static SurveyDataModel loadStoredOffer(Context context){
        SharedPreferences sharedPreferences
                = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);

        //load it from our prefs
        String data = sharedPreferences.getString(SURVEY_NAME, "");

        //try to create our survey object
        SurveyDataModel survey;

        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(data);// response will be the json String
            survey = gson.fromJson(object, SurveyDataModel.class);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return survey;
    }

}
