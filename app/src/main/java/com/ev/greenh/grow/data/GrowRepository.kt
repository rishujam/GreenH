package com.ev.greenh.grow.data

import com.ev.greenh.grow.data.model.res.GrowDetailRes
import com.ev.greenh.repository.BaseRepository
import com.ev.greenh.util.DummyData
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

class GrowRepository @Inject constructor() : BaseRepository() {

    fun getGrowDetails(plantId: String): GrowDetailRes {
        return DummyData.getGrowDetailData()
    }

    fun getPlantsByState(state: String) {

    }

    fun getPlantsBySunlight(answer: String) {

    }
}