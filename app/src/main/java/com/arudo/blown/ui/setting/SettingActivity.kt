package com.arudo.blown.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arudo.blown.R

class SettingActivity : AppCompatActivity() {

    companion object {
        const val keyPrefNotification = "notification_switch"
        const val changePrefNotification = "change_notification"
        const val channelId = 0
        const val channelTextId = "channel_id"
        const val channelName = "channel_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingFragment())
            .commit()
        supportActionBar?.title = getString(R.string.title_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}