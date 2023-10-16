package com.ev.greenh.plantidentification.data.model.res

import com.ev.greenh.plantidentification.data.model.common.Likelihood

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