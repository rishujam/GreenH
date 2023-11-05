package com.ev.greenh.common.commondata.plantidentifyres

import com.ev.greenh.plantidentify.data.model.res.Gbif
import com.ev.greenh.plantidentify.data.model.res.Image
import com.ev.greenh.plantidentify.data.model.res.Iucn
import com.ev.greenh.plantidentify.data.model.res.Powo
import com.ev.greenh.plantidentify.data.model.res.Species

data class Result(
    val gbif: Gbif,
    val images: List<Image>,
    val iucn: Iucn,
    val powo: Powo,
    val score: Float,
    val species: Species
)