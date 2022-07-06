package com.ev.greenh

import android.app.Application
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