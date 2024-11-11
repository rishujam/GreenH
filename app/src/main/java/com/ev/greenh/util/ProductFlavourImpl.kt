package com.ev.greenh.util

import com.core.util.ProductFlavour
import com.ev.greenh.BuildConfig
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 26/10/24.
 */

class ProductFlavourImpl @Inject constructor() : ProductFlavour {

    override fun get(): String {
        return BuildConfig.FLAVOR
    }
}