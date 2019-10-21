package com.example.cookbook

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class NetworkUtil {
    companion object {
        fun isConnectedWifi(context: Context): Boolean {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = cm.activeNetwork
            val cap = cm.getNetworkCapabilities(network)
            return cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
}