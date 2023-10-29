package com.ev.greenh.common.commondata.api

import retrofit2.http.GET

/*
 * Created by Sudhanshu Kumar on 18/07/23.
 */

interface MockyTestApi {

    @GET("e66a6409-9745-45b9-80c2-1a90a5b4e87d")
    fun getData()

}