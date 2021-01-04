package com.newpath.jeg.tapresearchtakehome.ui.SurveyOffer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.newpath.jeg.tapresearchtakehome.MainActivity;
import com.newpath.jeg.tapresearchtakehome.R;
import com.newpath.jeg.tapresearchtakehome.models.SurveyDataModel;
import com.newpath.jeg.tapresearchtakehome.ui.SurveyWebview.SurveyWebviewFragment;

/**
 * Fragment dedicated to fetching and launching available surveys
 */
public class SurveyOfferFragment extends Fragment {

    private SurveyOfferViewModel mSurveyViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey_offer, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnGetSurvey = view.findViewById(R.id.btn_open_survey);

        //setup viewmodel, we share this view model with SurveyWebviewFragment.java
        mSurveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyOfferViewModel.class);
        mSurveyViewModel.getSurvey(); //fetch our survey on view init
        Toast.makeText(getContext(), "Fetching Survey..", Toast.LENGTH_LONG).show();

        mSurveyViewModel.surveyLiveData.observe(requireActivity(), new Observer<SurveyDataModel>() {
            @Override
            public void onChanged(SurveyDataModel surveyDataModel) {
                if (surveyDataModel != null && surveyDataModel.getHasOffer()){
                    //if survey is succesfully fetched and has offer, enable button
                    btnGetSurvey.setEnabled(true);
                } else {
                    btnGetSurvey.setEnabled(false);
                    Snackbar snackbar = Snackbar
                            .make(view, "No Survey Currently Available", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });

        btnGetSurvey.setEnabled(false); //disble our button until survey is available
        btnGetSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launch webview
                openWebViewFragment();
            }
        });

    }

    public void openWebViewFragment(){
        ((MainActivity)getActivity()).addFragmentBackstack(android.R.id.content,
                new SurveyWebviewFragment(),
                "SurveyWV");
    }


}
