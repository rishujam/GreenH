package com.ev.greenh

import com.core.util.ProductFlavour
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 26/10/24.
 */

class ProductFlavourImpl @Inject constructor() : ProductFlavour {

    override fun get(): String {
        return BuildConfig.FLAVOR
    }
}