package com.example.auth.domain.usecase

import com.core.util.Constants
import com.core.util.Resource
import com.example.auth.data.repository.PhoneAuthRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 06/02/24.
 */

class SendOtpUseCase @Inject constructor(
    private val phoneAuthRepo: PhoneAuthRepository
) {
    operator fun invoke(phone: String): Flow<Resource<Boolean>> = flow {
        if(phone.length != 10) {
            emit(Resource.Error("Invalid number"))
        } else {
            emit(Resource.Loading())
            phoneAuthRepo.sendCredentials(phone).collect {
                it?.let {
                    if(it.Status == Constants.Other.SUCCESS_STRING) {
                        emit(Resource.Success(true))
                    } else {
                        emit(Resource.Error("unable to send otp"))
                    }
                } ?: run {
                    emit(Resource.Error("null"))
                }
            }
        }
    }
}