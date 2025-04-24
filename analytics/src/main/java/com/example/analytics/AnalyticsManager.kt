package com.example.analytics

class AnalyticsManager private constructor(
    private val logger: Logger,
    private val config: AnalyticsConfig
) {

    companion object {
        @Volatile
        private var instance: AnalyticsManager? = null

        @JvmStatic
        fun init(logger: Logger, config: AnalyticsConfig) {
            instance = AnalyticsManager(logger, config)
        }

        @JvmStatic
        fun get(): AnalyticsManager {
            return instance ?: error("AnalyticsManager is not initialized. Call AnalyticsManager.init(...) first.")
        }
    }

    fun logEvent(event: Event.Builder) {
        event.userId(config.userId)
        logger.logEvent(
            event.build()
        )
    }

}