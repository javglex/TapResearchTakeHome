package com.newpath.jeg.tapresearchtakehome.ui.SurveyWebview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.newpath.jeg.tapresearchtakehome.R;
import com.newpath.jeg.tapresearchtakehome.models.SurveyDataModel;
import com.newpath.jeg.tapresearchtakehome.ui.SurveyOffer.SurveyOfferViewModel;

/**
 * Fragment dedicated to loading webview and handling any client changes
 */
public class SurveyWebviewFragment extends Fragment {

    private SurveyOfferViewModel mSurveyViewModel;
    public static final String TAG = "SurveyWebviewFrag";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey_webview, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSurveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyOfferViewModel.class);
        launchWebView(view, mSurveyViewModel.surveyLiveData.getValue());

    }

    /**
     * setup and load survey url into a webview
     * @param view
     * @param survey - required for url to load survey from
     */
    public void launchWebView(View view, SurveyDataModel survey){

        if (survey == null)
            return;

        WebView web = (WebView) view.findViewById(R.id.webview_survey);
        web.setVisibility(View.VISIBLE);

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setPluginState(WebSettings.PluginState.ON);

        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) { //listens for changes in page urls
                super.onPageFinished(view, url);
                Log.i(TAG, survey.getAbandonUrl() + " ||| " + url);
                Log.i(TAG, "offer url: " + survey.getOfferUrl());
                if(url.contains("status") && url.contains("tid")){ //very basic check to simulate our survey is completed and closes
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                    //TODO: We can use our shared view model to emit a 'surveycompleted' event, forcing a new survey fetch. For another time...
                }
            }
        });

        if(survey.getOfferUrl() != null || survey.getOfferUrl() != "")
            web.loadUrl(survey.getOfferUrl());
    }


}
