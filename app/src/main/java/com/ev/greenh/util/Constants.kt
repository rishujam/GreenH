package com.ev.greenh.util

object Constants {

    const val RAZORPAY_BASE_URL ="https://razorpay-orderid-genrate.herokuapp.com"
    const val PLANT_NET_BASE_URL ="https://my-api.plantnet.org"
    const val GOOGLE_VISION_BASE_URL ="https://vision.googleapis.com/"
    const val MOCKY_BASE_URL ="https://run.mocky.io/v3/"

    const val VERSION = 1
    const val QUERY_PAGE_SIZE = 5

    object FirebaseCollection {
        const val FEATURE = "features"
    }

    object ViewType {
        const val LOCAL_PLANT_LIST = 0
        const val LOCAL_PLANT_LIST_QUESTION = 1
    }

    object Feature {
        const val GROW = "grow"
        const val SHOP = "shop"
        const val IDENTIFY = "identify"
    }

    fun getFallbackFeatureMap(): Map<String, Boolean> {
        return mapOf(Feature.IDENTIFY to true, Feature.GROW to false, Feature.SHOP to false)
    }
}