package com.core.data.remote

/*
 * Created by Sudhanshu Kumar on 04/07/23.
 */

sealed class ApiIdentifier {
    object PlantNetApi : ApiIdentifier()
    object MockyTestApi: ApiIdentifier()
    object Razorpay: ApiIdentifier()
    object TwoFactor: ApiIdentifier()
}
