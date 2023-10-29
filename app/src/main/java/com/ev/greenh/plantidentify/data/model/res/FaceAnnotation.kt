package com.ev.greenh.plantidentify.data.model.res

import com.ev.greenh.plantidentify.data.model.common.Likelihood

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class FaceAnnotation(
    val boundingPoly: BoundingPolyRes,
    val fdBoundingPoly: BoundingPolyRes,
    val landmarks: List<Landmark>,
    val rollAngle: Double,
    val panAngle: Double,
    val tiltAngle: Double,
    val detectionConfidence: Double,
    val landmarkingConfidence: Double,
    val joyLikelihood: Likelihood,
    val sorrowLikelihood: Likelihood,
    val angerLikelihood: Likelihood,
    val surpriseLikelihood: Likelihood,
    val underExposedLikelihood: Likelihood,
    val blurredLikelihood: Likelihood,
    val headwearLikelihood: Likelihood
)