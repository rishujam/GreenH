package com.ev.greenh

import android.app.Application
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.core.util.Constants
import com.example.analytics.AnalyticsConfig
import com.example.analytics.AnalyticsManager
import com.example.analytics.Event
import com.example.analytics.FirebaseLogger
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GreenApp : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance())
        AnalyticsManager.init(FirebaseLogger(), AnalyticsConfig(""))
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                super<DefaultLifecycleObserver>.onStart(owner)
                Log.d("LifecycleEvent", "onStart")
            }

            override fun onStop(owner: LifecycleOwner) {
                super<DefaultLifecycleObserver>.onStop(owner)
                Log.d("LifecycleEvent", "onStop")
            }
        })
        AnalyticsManager.get().logEvent(
            Event.Builder()
                .eventName(Constants.EventName.LAUNCH)
                .screenName(Constants.ScreenName.APP)
        )

    }

    @OptIn(ExperimentalCoilApi::class)
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(5 * 1024 * 1024)
                    .build()
            }
            .logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }
}