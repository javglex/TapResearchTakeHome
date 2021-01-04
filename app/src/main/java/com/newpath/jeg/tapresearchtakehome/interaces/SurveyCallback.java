package com.newpath.jeg.tapresearchtakehome.interaces;

import com.newpath.jeg.tapresearchtakehome.models.SurveyDataModel;

public interface SurveyCallback {
    public void onSuccess(SurveyDataModel survey);
    public void onFailure(String err);
}
