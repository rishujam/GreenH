package com.ev.greenh.grow.ui

import androidx.lifecycle.ViewModel
import com.ev.greenh.grow.data.GrowRepository
import com.ev.greenh.grow.data.model.res.GrowDetailRes

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

class GrowViewModel(
    private val repo: GrowRepository
) : ViewModel() {

    fun getGrowDetail(plantId: String): GrowDetailRes {
        return repo.getGrowDetails(plantId)
    }
}