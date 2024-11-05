package com.example.auth.domain.usecase

import com.core.data.Constants
import com.core.util.Resource
import com.example.auth.data.model.UserProfile
import com.example.auth.data.repository.PhoneAuthRepository
import com.example.auth.data.repository.UserDataRepository
import com.example.auth.domain.model.SignUpComplete
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 06/02/24.
 */

class OtpSignUpUseCase @Inject constructor(
    private val phoneAuthRepository: PhoneAuthRepository,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(
        phone: String,
        otp: String,
        version: Int
    ): Flow<Resource<SignUpComplete>> = flow {
        if(otp.length != OTP_LENGTH) {
            emit(Resource.Error("Enter correct OTP"))
        } else {
            emit(Resource.Loading())
            phoneAuthRepository.verifyCredentials(phone, otp).collect {
                it?.let {
                    if(it.Status == Constants.Other.SUCCESS_STRING) {
                        val userRes = userDataRepository.checkUserExist(phone)
                        val userExist = userRes.exist
                        if(userExist == true) {
                            val uid = userRes.userProfile?.uid
                            handleMsgTokenAndUid(uid)
                            userDataRepository.setLoggedIn(true)
                            emit(
                                Resource.Success(
                                    SignUpComplete(
                                        newUser = false
                                    )
                                )
                            )
                        } else {
                            val lastUid = userDataRepository.getLastGeneratedUid()
                            val generatedUid = lastUid.uid.toString().toInt() + 1
                            val profile = UserProfile(
                                phone = phone,
                                uid = generatedUid.toString(),
                                profileComplete = false,
                                version = version
                            )
                            userDataRepository.updateLastUid(generatedUid.toString())
                            val userDataRes = userDataRepository.saveUserData(profile)
                            handleMsgTokenAndUid(generatedUid.toString())
                            userDataRepository.setLoggedIn(true)
                            emit(
                                Resource.Success(
                                    SignUpComplete(
                                        savedUserDataOnServer = userDataRes.success,
                                        newUser = true
                                    )
                                )
                            )
                        }
                    } else {
                        emit(Resource.Error("Invalid OTP"))
                    }
                } ?: run {
                    emit(Resource.Error("null"))
                }
            }
        }
    }

    private suspend fun handleMsgTokenAndUid(uid: String?) {
        userDataRepository.saveUidLocally(uid)
        val token = userDataRepository.generateFirebaseMsgToken(uid)
        if(token?.success == true) {
            userDataRepository.saveFirebaseMsgTokenLocally(token.token.toString())
        }
    }

    companion object {
        private const val OTP_LENGTH = 4
    }

}