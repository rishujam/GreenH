package com.ev.greenh

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.ev.greenh.localdatastore.UserPreferences

class GreenApp : Application(), ImageLoaderFactory {
    private lateinit var userPref: UserPreferences

    override fun onCreate() {
        super.onCreate()
        userPref = UserPreferences(this)
    }

    val userPreferences: UserPreferences
        get() {
            return userPref
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