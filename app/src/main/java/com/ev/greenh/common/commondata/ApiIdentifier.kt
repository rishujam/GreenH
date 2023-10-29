package com.ev.greenh.common.commondata

/*
 * Created by Sudhanshu Kumar on 04/07/23.
 */

sealed class ApiIdentifier {
    object VisionApi : ApiIdentifier()
    object PlantNetApi : ApiIdentifier()
    object MockyTestApi: ApiIdentifier()
    object Razorpay: ApiIdentifier()
}
