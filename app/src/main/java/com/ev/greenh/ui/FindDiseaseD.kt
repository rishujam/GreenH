package com.ev.greenh.ui

/*
 * Created by Sudhanshu Kumar on 16/05/23.
 */

object FindDiseaseD {

    fun getD(name: String): String? {
        return when(name) {
            "0b3e5032-8ae8-49ac-8157-a1cac3df01dd___RS_HL 1817_flipLR.JPG" -> { // potato_healthy picture 1
                null
            }
            "0a5e9323-dbad-432d-ac58-d291718345d9___FREC_Scab 3417_new30degFlipLR.JPG" -> { //apple_scab picture 1
                "Apple Scab"
            }
            "0a01cc10-3892-4311-9c48-0ac6ab3c7c43___RS_GLSp 9352_new30degFlipLR.JPG" -> { //Corn picture 1
                "Cercospora grey spot leaf"
            }
            "0a02e8cb-b715-497f-a16a-c28b3409f927___RS_HL 7432.JPG" -> { //healthy apple picture 1
                null
            }
            "0b8dabb7-5f1b-4fdc-b3fa-30b289707b90___JR_FrgE.S 3047_90deg.JPG" -> { //apple picture 1
                "Black rot"
            }
            else -> {null}
        }
    }
}