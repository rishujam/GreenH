package com.ev.greenh.util

import com.ev.greenh.grow.ui.model.LocalPlantListItem
import com.ev.greenh.grow.ui.model.LocalPlantListItemBase
import com.ev.greenh.grow.ui.model.LocalPlantListQuestionItem
import com.ev.greenh.grow.ui.model.Option
import com.ev.greenh.grow.ui.model.SelectAreaListItem

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
                Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "1",
                "Rose",
                "",
                Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListQuestionItem(
                "2",
                "Tell us more",
                "How much sunlight reaches your space?",
                listOf(
                    Option("Direct"),
                    Option("Indirect"),
                    Option("No Sunlight")
                ),
                viewType = Constants.ViewType.LOCAL_PLANT_LIST_QUESTION
            ),
            LocalPlantListItem(
                "3",
                "Rose",
                "",
                Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "4",
                "Rose",
                "",
                Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "5",
                "Rose",
                "",
                Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "6",
                "Rose",
                "",
                Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "7",
                "Rose",
                "",
                Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "8",
                "Rose",
                "",
                Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "9",
                "Rose",
                "",
                Constants.ViewType.LOCAL_PLANT_LIST
            ),
            LocalPlantListItem(
                "10",
                "Rose",
                "",
                Constants.ViewType.LOCAL_PLANT_LIST
            )
        )
    }
}