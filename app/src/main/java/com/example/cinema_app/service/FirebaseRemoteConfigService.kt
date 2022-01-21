package com.example.cinema_app.service

import com.example.cinema_app.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

interface FirebaseRemoteConfigService {
    fun getRemoteConfig(): FirebaseRemoteConfig {
        var remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.default_config)
        remoteConfig.fetchAndActivate()
        return remoteConfig
    }
}