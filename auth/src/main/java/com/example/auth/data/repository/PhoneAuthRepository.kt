package com.example.auth.data.repository

import com.example.auth.data.model.ResSendOtp
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.Flow

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

interface PhoneAuthRepository {

    suspend fun sendCredentials(phone: String): Flow<ResSendOtp?>

    suspend fun resendCredentials(
        phone: String,
        resendToken: PhoneAuthProvider.ForceResendingToken
    ): Flow<ResSendOtp?>

    suspend fun verifyCredentials(
        verifyId: String,
        otp: String
    ): String?

}