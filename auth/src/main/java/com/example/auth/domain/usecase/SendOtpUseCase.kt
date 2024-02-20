package com.example.auth.domain.usecase

import com.core.data.Constants
import com.core.util.Resource
import com.example.auth.data.repository.PhoneAuthRepository
import com.example.auth.domain.model.OnSignUp
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
        emit(Resource.Loading())
        phoneAuthRepo.sendCredentials(phone).collect {
            it?.let {
                if(it.Status == Constants.Other.SUCCESS_STRING) {
                    coroutineScope {
                        emit(Resource.Success(true))
                    }
                } else {
                    emit(Resource.Error("unable to send otp"))
                }
            } ?: run {
                emit(Resource.Error("null"))
            }
        }
    }

}