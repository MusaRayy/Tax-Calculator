package com.musarayy.taxcalculator.services

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.musarayy.taxcalculator.ui.home.LandingActivity
import com.musarayy.taxcalculator.utils.DataCache
import com.musarayy.taxcalculator.utils.OSALogger
import com.musarayy.taxcalculator.utils.mLogException
import org.koin.android.ext.android.inject

class MyFireBaseMessagingService : FirebaseMessagingService() {

    val dataCache: DataCache by inject()
    val logger: OSALogger by inject()

    override fun onNewToken(token: String) = dataCache.setDeviceTokenId(token)

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try {
            remoteMessage.data.let { data ->
                val id: String = data.get("id").toString()
                val type: String = data.get("type").toString()
                val title: String = data.get("title").toString()
                val message: String = data.get("message").toString()
                val intent = Intent(this, LandingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                NotificationHelper(
                    this
                ).showAppChannelNotification(title, message, intent)
            }

            logger.d(remoteMessage.data.toString())
        } catch (e: Exception) {
            e.mLogException(logger)
        }
    }
}