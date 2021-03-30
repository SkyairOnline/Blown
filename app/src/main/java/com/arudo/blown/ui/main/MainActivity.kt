package com.arudo.blown.ui.main

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.arudo.blown.R
import com.arudo.blown.ui.setting.SettingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_favorite
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notifyIntent = Intent(this, AlarmReceiver::class.java)
        val notifyPendingIntent = PendingIntent.getBroadcast(
            this,
            SettingActivity.channelId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        PreferenceManager.setDefaultValues(this, R.xml.preference_settings, false)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val switchOnOrOff = sharedPreferences.getBoolean(SettingActivity.keyPrefNotification, false)
        if (switchOnOrOff) {
            setInExactRepeating(alarmManager, notifyPendingIntent)
        } else {
            notificationManager.cancelAll()
            alarmManager.cancel(notifyPendingIntent)
        }
        createChannelNotification()
    }

    private fun setInExactRepeating(alarmManager: AlarmManager, pendingIntent: PendingIntent) {
        val repeatTimeInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES
        val triggerTime = SystemClock.elapsedRealtime() + repeatTimeInterval
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            repeatTimeInterval,
            pendingIntent
        )
    }

    private fun createChannelNotification() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                SettingActivity.channelTextId,
                SettingActivity.channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_description)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}