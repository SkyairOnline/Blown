package com.arudo.blown.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.arudo.blown.R

class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey)
    }

}