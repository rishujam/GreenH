package com.ev.greenh.plantidentify.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class DetectedBreak(
    val type: BreakType,
    val isPrefix: Boolean
) {
    enum class BreakType {
        UNKNOWN,
        SPACE,
        SURE_SPACE,
        EOL_SURE_SPACE,
        HYPHEN,
        LINE_BREAK
    }
}