package com.arudo.blown.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.arudo.blown.R
import com.arudo.blown.ui.main.MainActivity
import com.arudo.blown.ui.setting.SettingActivity.Companion.channelId
import com.arudo.blown.ui.setting.SettingActivity.Companion.channelTextId

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        deliverNotification(context)
    }

    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            channelId,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, channelTextId)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(context.getString(R.string.notification_settings))
            .setContentText(context.getString(R.string.notification_description))
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        notificationManager.notify(channelId, builder.build())
    }
}