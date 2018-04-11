package com.adonis.tratamientodeimagenes.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

public final class NetworkUtils {

    private NetworkUtils() {
        //do nothing
    }

    @SuppressLint("MissingPermission")
    public static boolean isConnected(@NonNull Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void openWifiSettings(@NonNull final Activity activity) {
        activity.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
    }
}
