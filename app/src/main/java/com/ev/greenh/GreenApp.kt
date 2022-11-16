package com.ev.greenh

import android.app.Application
import androidx.media3.database.ExoDatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.ev.greenh.localdatastore.UserPreferences

class GreenApp : Application() {
    private lateinit var userPref: UserPreferences

    override fun onCreate() {
        super.onCreate()
        userPref = UserPreferences(this)
    }

    val userPreferences: UserPreferences
        get() {
            return userPref
        }
}