package com.example.cookbook

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if(NetworkUtil.isConnectedWifi(context)){
            NewRecipesNotificationService.startActionNewRecipes(context)
        }
    }
}
