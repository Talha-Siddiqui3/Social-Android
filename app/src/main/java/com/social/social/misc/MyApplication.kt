package com.social.social.misc

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
class MyApplication : Application() {



    override fun onCreate() {
        super.onCreate()
//        FirebaseApp.initializeApp(applicationContext)
//        VolleyDownloadClassSync.initializeMyVolleyDownloadClass(this)
        createNotificationChannel()
        MyApplication.applicationContext = applicationContext as Application
    }

    companion object {
        lateinit var applicationContext:Application
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "All Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("2", name, importance).apply {
                setShowBadge(false)
            }
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(channel)
        }
    }

}