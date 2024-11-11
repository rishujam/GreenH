package com.example.auth.test.fakes

import com.core.data.model.ResUidGen
import com.example.auth.data.model.ResFirebaseMsgToken
import com.example.auth.data.model.ResGetProfile
import com.example.auth.data.model.ResSaveProfile
import com.example.auth.data.model.ResUserExist
import com.example.auth.data.model.UserProfile
import com.example.auth.data.repository.UserDataRepository
import com.example.auth.test.util.Constants
import kotlin.coroutines.CoroutineContext

/*
 * Created by Sudhanshu Kumar on 22/02/24.
 */

class UserDataRepoFakeImpl : UserDataRepository {

    override suspend fun checkUserExist(phone: String): ResUserExist {
        return when(phone) {
            Constants.EXISTING_USER_PHONE -> {
                ResUserExist(
                    success = true,
                    exist = true,
                    userProfile = UserProfile(uid = Constants.MOST_RECENT_USER_UID.toString())
                )
            }

            Constants.USER_EXIST_ERROR_PHONE -> {
                ResUserExist(
                    success = false,
                    msg = "Error"
                )
            }

            Constants.NEW_USER -> {
                ResUserExist(
                    success = true,
                    exist = false
                )
            }

            else -> {
                ResUserExist(
                    success = true,
                    exist = false
                )
            }
        }
    }

    override suspend fun saveUserData(profile: UserProfile): ResSaveProfile {
        return ResSaveProfile(success = true)
    }

    override suspend fun deleteUserData(uid: String?): ResSaveProfile {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(uid: String): ResGetProfile {
        TODO("Not yet implemented")
    }

    override suspend fun saveUidLocally(uid: String?) {
        //TODO Test preferences
    }

    override suspend fun getUid(): String {
        return Constants.MOST_RECENT_USER_UID.toString()
    }

    override suspend fun updateLastUid(newUid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getLastGeneratedUid(): ResUidGen {
        return ResUidGen(
            success = true,
            uid = Constants.MOST_RECENT_USER_UID.toString()
        )
    }

    override suspend fun generateFirebaseMsgToken(uid: String?): ResFirebaseMsgToken? {
        return ResFirebaseMsgToken(success = true, token = Constants.FIREBASE_MSG_TOKEN)
    }

    override suspend fun saveFirebaseMsgTokenLocally(token: String) {
        //TODO Test pref
    }

    override suspend fun setLoggedIn(loggedIn: Boolean) {
        // TODO Test preferences
    }

    override suspend fun isLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun isLoginSkipped(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setLoginSkipped() {

    }

    override suspend fun clearUserPref() {
        TODO("Not yet implemented")
    }
}