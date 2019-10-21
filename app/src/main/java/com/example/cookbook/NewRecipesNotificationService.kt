package com.example.cookbook

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

private const val ACTION_BAZ = "com.example.cookbook.action.BAZ"

private const val CHANNEL_ID = "com.example.cookbook.NEW_RECIPES"

class NewRecipesNotificationService : IntentService("NewRecipesNotificationService") {

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_BAZ -> {
                notifyAboutNewRecipes()
            }
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun notifyAboutNewRecipes() {
        val recipes = Firebase.firestore.collection("recipes")
        val context = this
        recipes.get().addOnSuccessListener {
            if (it.size() > 0)
                notify(context, it.size())
        }

    }

    private fun notify(context: Context, count: Int) {
        createNotificationChannel()
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_adb_black_24dp)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_content_start) + count.toString() + getString(R.string.notification_content_end))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            val notificationId = Random.nextInt()
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    companion object {
        @JvmStatic
        fun startActionNewRecipes(context: Context) {
            val intent = Intent(context, NewRecipesNotificationService::class.java).apply {
                action = ACTION_BAZ
            }
            context.startService(intent)
        }
    }
}
