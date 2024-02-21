package com.ev.greenh.grow.ui

import androidx.lifecycle.ViewModel
import com.ev.greenh.grow.data.GrowRepository
import com.ev.greenh.grow.ui.events.LocalPlantListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@HiltViewModel
class LocalPlantListViewModel @Inject constructor (
    private val repo: GrowRepository
) : ViewModel() {

    fun onClick(event: LocalPlantListEvent) {
        when(event) {
            is LocalPlantListEvent.OnQuestionSubmitClick -> {

            }
        }
    }

}