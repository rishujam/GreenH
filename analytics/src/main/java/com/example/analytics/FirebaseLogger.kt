package com.example.analytics


class FirebaseLogger : Logger {

    override fun logEvent(
        event: Event
    ) {
//        Firebase.analytics.logEvent(event.name) {
//            param(AnalyticsKey.ID, event.id)
//            param(AnalyticsKey.TIME_STAMP, event.timeStamp)
//            param(AnalyticsKey.SCREEN_NAME, event.screenName)
//        }
    }

}