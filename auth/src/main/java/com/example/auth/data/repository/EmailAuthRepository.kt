package com.example.auth.data.repository

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

interface EmailAuthRepository {

    fun signIn(email: String, password: String)

    fun singUp(email: String, password: String)

    fun sendCredentialForgetPassword(email: String)

    fun verifyCredentialForgetPassword(email: String, otp: String)

    fun updatePassword(email: String, newPassword: String)

}