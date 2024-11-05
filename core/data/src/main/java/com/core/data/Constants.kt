package com.core.data

/*
 * Created by Sudhanshu Kumar on 04/02/24.
 */

object Constants {
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
    }

    object FirebaseDoc {
        const val TODAY_TIP = "today"
        const val UPDATE = "update"
        const val MAINTENANCE = "maintenance"
        const val UID_GEN = "uidgen"
        const val FEATURE = "features"
    }

    object FirebaseField {
        const val TOKEN = "token"
        const val UID = "uid"
    }

    object Args {
        const val BUILD_VERSION = "build_version"
        const val PROFILE = "profile"
        const val RESULT_USER_LOGGED_IN = "RESULT_USER_LOGGED_IN"
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



}