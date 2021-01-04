package com.newpath.jeg.tapresearchtakehome.models;

public class SurveyDataModel {
    String offerUrl;
    String abandonUrl;
    Boolean hasOffer;
    Long timeFetched;   //for checking if older than 30 seconds

    public SurveyDataModel(String offerUrl, String abandonUrl, Boolean hasOffer, Long timeFetched){
        this.offerUrl = offerUrl;
        this.abandonUrl = abandonUrl;
        this.hasOffer = hasOffer;
        this.timeFetched = timeFetched;
    }

    public String getOfferUrl() {
        return offerUrl;
    }

    public String getAbandonUrl() {
        return abandonUrl;
    }

    public Boolean getHasOffer() {
        return hasOffer;
    }

    public Long getTimeFetched() {
        return timeFetched;
    }
}
