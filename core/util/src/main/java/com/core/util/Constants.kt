package com.core.util

/*
 * Created by Sudhanshu Kumar on 04/02/24.
 */

object Constants {

    const val VERSION = 1
    const val QUERY_PAGE_SIZE = 5

    object BaseUrl {
        const val RAZORPAY ="https://razorpay-orderid-genrate.herokuapp.com"
        const val PLANT_NET ="https://my-api.plantnet.org"
        const val MOCKY ="https://run.mocky.io/v3/"
        const val TWO_FACTOR ="https://2factor.in/"
    }

    object FirebaseColl {
        const val TIPS = "tips"
        const val APP_CONFIG = "appconfig"
        const val ANALYTICS = "analytics"
        const val COLL_PROFILE = "users"
        const val FEATURE_CONFIG = "feature_config"
        const val UTIL = "util"
        const val MSG_TOKEN = "msgtoken"
        const val PLANTS = "plants"
    }

    object FirebaseDoc {
        const val TODAY_TIP = "today"
        const val UPDATE = "update"
        const val MAINTENANCE = "maintenance"
        const val UID_GEN = "uidgen"
        const val FEATURE = "features"
        const val LAST_PLANT_ID = "lastPlantId"
    }

    object FirebaseField {
        const val TOKEN = "token"
        const val UID = "uid"
        const val ID = "id"
        const val NAME = "name"
    }

    object Args {
        const val START_FOR_RESULT = "start_for_result"
        const val BUILD_VERSION = "build_version"
        const val PROFILE = "profile"
        const val RESULT_USER_LOGGED_IN = "RESULT_USER_LOGGED_IN"
        const val RESULT_PROFILE_UPDATE = "RESULT_PROFILE_UPDATE"
        const val PLANT_ID = "plant_id"
    }

    object Other {
        const val SUCCESS_STRING = "Success"
        const val ERROR_STRING = "Error"
        const val EMPTY_STRING = ""
    }

    object Pref {
        const val CONFIG = "pref_config"
        const val AUTH = "pref_auth"
        const val COMMON = "pref_common"
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

    object FragTags {
        const val IDENTIFY_FRAG = "identify_frag"
    }

    object ScreenName {
        const val APP = "APP"
    }

    object EventName {
        const val LAUNCH = "LAUNCH"
    }


}