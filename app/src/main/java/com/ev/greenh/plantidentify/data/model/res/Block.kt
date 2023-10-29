package com.ev.greenh.plantidentify.data.model.res

import com.ev.greenh.plantidentify.data.model.common.BoundingPoly

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class Block(
    val property: TextProperty,
    val boundingBox: BoundingPoly,
    val paragraphs: List<Paragraph>,
    val blockType: BlockType,
    val confidence: Double
) {
    enum class BlockType {
        UNKNOWN,
        TEXT,
        IMAGE
    }
}