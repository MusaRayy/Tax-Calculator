package com.musarayy.taxcalculator.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.musarayy.taxcalculator.BuildConfig
import com.musarayy.taxcalculator.R
import java.util.*

class NotificationHelper(private val mContext: Context) : ContextWrapper(mContext) {

    private val NOTIFICATION_CHANNEL_ID: String = BuildConfig.APPLICATION_ID
    private val NOTIFICATION_CHANNEL_NAME: String = "$NOTIFICATION_CHANNEL_ID Channel"
    private var manager: NotificationManager? = null


    init {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels()
        }
    }


    private fun createChannels() {

        // IMPORTANCE DEFAULT = show everywhere, make noise, but don't visually intrude
        // IMPORTANCE HIGH = show everywhere, make noise and peeks
        // IMPORTANCE LOW = show everywhere, but isn't intrusive
        // IMPORTANCE MIN = only show in the shad, below the fold
        // IMPORTANCE NONE = a notification with no importance, don;t show the shade
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lightColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            getManager()?.createNotificationChannel(notificationChannel)
        }
    }

    private fun getManager(): NotificationManager? {
        if (manager == null) manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return manager
    }

    @Suppress("DEPRECATION")
    fun showAppChannelNotification(title: String?, message: String?, intent: Intent?) {

        val number = Random().nextInt(9999 - 1000) + 1000
        var contentIntent: PendingIntent? = null
        if (intent != null) contentIntent =
            PendingIntent.getActivity(this, number, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                    .setContentText(message)
                    .setContentTitle(title)
                    .setSmallIcon(R.mipmap.applogo)
                    .setAutoCancel(true)
            if (contentIntent != null) builder.setContentIntent(contentIntent)
            getManager()!!.notify(number, builder.build())
        } else {
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext)
                .setDefaults(Notification.DEFAULT_ALL)
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.applogo)
                /*.setContentTitle(title)
                .setContentText(message)*/
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.applogo))
                .setTicker(BuildConfig.BUILD_TYPE)
                .setWhen(System.currentTimeMillis())
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(message).setBigContentTitle(title)
                )
                .setAutoCancel(true)

            if (contentIntent != null) builder.setContentIntent(contentIntent)
            val nm =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify("NewMessage", number, builder.build())
        }
    }
}