package com.ev.greenh.plantidentify.data.model.res

import com.ev.greenh.plantidentify.data.model.common.Likelihood

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class SafeSearchAnnotation(
    val adult: Likelihood,
    val spoof: Likelihood,
    val medical: Likelihood,
    val violence: Likelihood,
    val racy: Likelihood
)