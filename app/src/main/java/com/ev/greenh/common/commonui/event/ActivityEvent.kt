package com.ev.greenh.common.commonui.event

import com.ev.greenh.common.commonui.model.DialogModel

/*
 * Created by Sudhanshu Kumar on 15/11/23.
 */

sealed class ActivityEvent {

    data class ShowDialog(val dialog: DialogModel) : ActivityEvent()

}