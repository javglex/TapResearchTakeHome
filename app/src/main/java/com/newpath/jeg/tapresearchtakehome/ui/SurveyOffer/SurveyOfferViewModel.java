package com.newpath.jeg.tapresearchtakehome.ui.SurveyOffer;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.newpath.jeg.tapresearchtakehome.interaces.SurveyCallback;
import com.newpath.jeg.tapresearchtakehome.models.SurveyDataModel;
import com.newpath.jeg.tapresearchtakehome.repo.SurveyRepo;

public class SurveyOfferViewModel extends AndroidViewModel {

    public MutableLiveData<SurveyDataModel> surveyLiveData = new MutableLiveData<>();

    public static final String TAG = "SurveyOfferViewModel";
    Handler mHandler;
    Runnable mRunnable;
    HandlerThread handlerThread;

    public SurveyOfferViewModel(Application app){
        super(app);
        handlerThread = new HandlerThread("NetworkThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        mHandler = new Handler(looper);
    }

    public void getSurvey(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SurveyRepo.getSurvey(getApplication(), new SurveyCallback() {
                    @Override
                    public void onSuccess(SurveyDataModel survey) {
                        surveyLiveData.postValue(survey); //we use post since we are running from another thread
                    }

                    @Override
                    public void onFailure(String err) {
                        Log.e(TAG, err);
                        surveyLiveData.postValue(null);

                    }
                });
            }
        };

        mHandler.post(runnable);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        handlerThread.quit();
        mHandler.removeCallbacks(mRunnable);
        mRunnable = null;
    }


}
