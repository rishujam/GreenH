package com.example.auth.data.repositoryimpl

import com.core.data.model.ResUidGen
import com.example.auth.data.localsource.UserDataPrefManager
import com.example.auth.data.model.ResFirebaseMsgToken
import com.example.auth.data.model.ResGetProfile
import com.example.auth.data.model.ResSaveProfile
import com.example.auth.data.model.ResUserExist
import com.example.auth.data.model.UserProfile
import com.example.auth.data.remotesource.UserDataSource
import com.example.auth.data.repository.UserDataRepository
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

class UserDataRepoImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userDataPrefManager: UserDataPrefManager
) : UserDataRepository {

    override suspend fun checkUserExist(phone: String): ResUserExist {
        return userDataSource.checkUserExist(phone)
    }

    override suspend fun saveUserData(profile: UserProfile): ResSaveProfile {
        if(profile.uid.isEmpty()) {
            return ResSaveProfile(success = false, msg = "Empty uid passed")
        }
        return userDataSource.saveUserProfile(profile)
    }
    override suspend fun deleteUserData(uid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(uid: String): ResGetProfile {
        return userDataSource.getProfile(uid)
    }

    override suspend fun saveUidLocally(uid: String?) {
        userDataPrefManager.setUid(uid)
    }

    override suspend fun getUid(): String? {
        return userDataPrefManager.readUid()
    }

    override suspend fun getLastGeneratedUid(): ResUidGen {
        return userDataSource.getLastGeneratedUid()
    }

    override suspend fun updateLastUid(newUid: String) {
        userDataSource.updateLastGeneratedUid(newUid)
    }

    override suspend fun generateFirebaseMsgToken(uid: String?): ResFirebaseMsgToken? {
        uid?.let {
            val token = userDataSource.getFirebaseMsgToken()
            token?.let {
                userDataSource.saveFirebaseMsgToken(uid = uid, token = token)
                return ResFirebaseMsgToken(true, token = it)
            } ?: run {
                return ResFirebaseMsgToken(false,  "null")
            }
        } ?: run {
            return null
        }
    }

    override suspend fun saveFirebaseMsgTokenLocally(token: String) {
        userDataPrefManager.setFirebaseMsgToken(token)
    }

    override suspend fun setLoggedIn(loggedIn: Boolean) {
        userDataPrefManager.setIsLoggedIn(loggedIn)
    }

    override suspend fun isLoggedIn(): Boolean? {
        return userDataPrefManager.isLoggedIn()
    }
}