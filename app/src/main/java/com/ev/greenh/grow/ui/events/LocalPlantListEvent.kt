package com.ev.greenh.grow.ui.events

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

sealed class LocalPlantListEvent {
    object OnQuestionSubmitClick : LocalPlantListEvent()
}