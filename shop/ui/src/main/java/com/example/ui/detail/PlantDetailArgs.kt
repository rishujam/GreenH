package com.example.ui.detail

import com.example.domain.model.Plant
import java.io.Serializable

/*
 * Created by Sudhanshu Kumar on 08/01/25.
 */

data class PlantDetailArgs(
    val id: String,
    val plant: Plant
) : Serializable
