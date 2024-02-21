package com.example.auth.data.repository

import com.example.auth.data.model.ResOtp
import kotlinx.coroutines.flow.Flow

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

interface PhoneAuthRepository {

    suspend fun sendCredentials(phone: String): Flow<ResOtp?>

    suspend fun verifyCredentials(
        phone: String,
        otp: String
    ): Flow<ResOtp?>

}