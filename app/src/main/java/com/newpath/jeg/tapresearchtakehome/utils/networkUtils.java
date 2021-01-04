package com.newpath.jeg.tapresearchtakehome.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class networkUtils {

    //returns true of either wifi or 4g is connected to the network
    public static boolean networkConnected (Context context) {

        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityMgr.getActiveNetworkInfo();
        NetworkInfo cellular = connectivityMgr.getActiveNetworkInfo();
        if (wifi != null && wifi.getType() == ConnectivityManager.TYPE_WIFI)
            return true;
        else
        if (cellular != null && cellular.getType() == ConnectivityManager.TYPE_MOBILE)
            return true;

        return false;

    }
}
