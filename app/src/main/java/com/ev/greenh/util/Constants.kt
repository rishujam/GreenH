package com.ev.greenh.util

object Constants {

    const val RAZORPAY_BASE_URL ="https://razorpay-orderid-genrate.herokuapp.com"
    const val PLANT_NET_BASE_URL ="https://my-api.plantnet.org"
    const val MOCKY_BASE_URL ="https://run.mocky.io/v3/"

    const val VERSION = 1
    const val QUERY_PAGE_SIZE = 5

    object ViewType {
        const val LOCAL_PLANT_LIST = 0
        const val LOCAL_PLANT_LIST_QUESTION = 1
    }

    object Feature {
        const val GROW = "grow"
        const val SHOP = "shop"
        const val IDENTIFY = "identify"
    }

    object FragTags {
        const val IDENTIFY_FRAG = "identify_frag"
    }

    fun getFallbackFeatureMap(): Map<String, Boolean> {
        return mapOf(Feature.IDENTIFY to true, Feature.GROW to false, Feature.SHOP to false)
    }
}