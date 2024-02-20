package com.ev.greenh.grow.ui

import androidx.lifecycle.ViewModel
import com.ev.greenh.grow.data.GrowRepository
import com.ev.greenh.grow.data.model.res.GrowDetailRes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@HiltViewModel
class GrowViewModel @Inject constructor (
    private val repo: GrowRepository
) : ViewModel() {

    fun getGrowDetail(plantId: String): GrowDetailRes {
        return repo.getGrowDetails(plantId)
    }
}