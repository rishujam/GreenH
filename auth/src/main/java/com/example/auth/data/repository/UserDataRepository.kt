package com.example.auth.data.repository

import com.core.data.model.ResUidGen
import com.example.auth.data.model.ResFirebaseMsgToken
import com.example.auth.data.model.ResGetProfile
import com.example.auth.data.model.ResSaveProfile
import com.example.auth.data.model.ResUserExist
import com.example.auth.data.model.UserProfile

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

interface UserDataRepository {

    suspend fun checkUserExist(phone: String): ResUserExist

    suspend fun saveUserData(profile: UserProfile): ResSaveProfile

    suspend fun deleteUserData(uid: String)

    suspend fun getUserData(uid: String): ResGetProfile

    suspend fun saveUidLocally(uid: String)

    suspend fun getUid(): String?

    suspend fun getRecentlyGeneratedUid(): ResUidGen

    suspend fun generateFirebaseMsgToken(uid: String): ResFirebaseMsgToken

    suspend fun saveFirebaseMsgTokenLocally(token: String)

}