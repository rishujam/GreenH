package com.example.auth.data.repository

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

interface UserDataRepository {

    fun saveUserData()

    fun updateUserData()

    fun deleteUserData()

    fun getUserData()

}