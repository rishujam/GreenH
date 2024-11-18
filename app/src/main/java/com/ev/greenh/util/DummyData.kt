package com.ev.greenh.util

import com.core.ui.model.RadioBtnOption
import com.ev.greenh.grow.data.model.res.GrowDetailRes
import com.ev.greenh.grow.ui.model.LocalPlantListItem
import com.ev.greenh.grow.ui.model.LocalPlantListItemBase
import com.ev.greenh.grow.ui.model.LocalPlantListQuestionItem

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

object DummyData {

    fun getListOfState(): List<String> {
        return mutableListOf(
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Delhi NCR",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal"
        )
    }

    fun getLocalPlantList(): List<LocalPlantListItemBase> {
        return mutableListOf(
            LocalPlantListItem(
                "0",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "1",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListQuestionItem(
                "2",
                "Tell us more",
                "How much sunlight reaches your space?",
                listOf(
                    RadioBtnOption("Direct"),
                    RadioBtnOption("Indirect"),
                    RadioBtnOption("No Sunlight")
                ),
                viewType =  com.core.data.Constants.ViewType.LOCAL_PLANT_LIST_QUESTION
            ),
            LocalPlantListItem(
                "3",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "4",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "5",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "6",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "7",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "8",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "9",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "10",
                "Rose",
                "",
                com.core.data.Constants.ViewType.LOCAL_PLANT_LIST
            )
        )
    }

    fun getGrowDetailData(): GrowDetailRes {
        return GrowDetailRes(
            "https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/sample_image.png?alt=media&token=7736e4e8-e0c6-40cd-9c54-8eac8dd4ec92",
            "Aglonema",
            mapOf(
                "Sunlight" to "Direct",
                "Maintenance" to "High",
                "Water" to "Daily"
            ),
            listOf("Buy plant", "Maintain plant"),
            true
        )
    }
}