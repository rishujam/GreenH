package com.ev.greenh.auth.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.auth.ui.events.SignUpEvents
import com.ev.greenh.auth.ui.events.SignUpUiEvents
import com.ev.greenh.auth.ui.states.SignUpProgress
import com.ev.greenh.models.Response
import com.ev.greenh.repository.AuthRepository
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.ViewModelFactory
import com.google.firebase.auth.PhoneAuthOptions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/*
 * Created by Sudhanshu Kumar on 17/07/23.
 */

class SignUpViewModelTest {

    private lateinit var viewModel: SignUpViewModel

    @Mock
    private lateinit var repository: AuthRepository

    private var eventLoading: SignUpUiEvents.Loading? = null
    private var eventToast: SignUpUiEvents.ShowToast? = null
    private var eventScreenChanged: SignUpUiEvents.ScreenChanged? = null

    @Before
    fun setUp() = runBlocking {
        MockitoAnnotations.initMocks(this)
//        val email = "test@example.com"
//        val password = "password"
//        val mockLoginResponse =
//            Mockito.when(repository.login(email, password)) {
//                Resource.Success(Response(true, null))
//            }

        repository = Mockito.mock(AuthRepository::class.java)
        viewModel = SignUpViewModel(repository)
        viewModel.eventFlow.collectLatest {
            when (it) {
                is SignUpUiEvents.ScreenChanged -> eventScreenChanged = it
                is SignUpUiEvents.Loading -> eventLoading = it
                is SignUpUiEvents.ShowToast -> eventToast = it
            }
        }
    }

    @Test
    fun `when event is NextClick, set phone and emit loading`() {
        val phone = "9999999999"
        viewModel.onEvent(SignUpEvents.NextClick(phone))
        var result = true
        if (viewModel.state.value.phoneNo != phone) result = false
        if (eventLoading != SignUpUiEvents.Loading(true)) result = false
        eventLoading = null
        assert(result)
    }

    @Test
    fun `when event is OtpOption and options is null, emit not loading and toast`() {
        viewModel.onEvent(SignUpEvents.OtpOption(null, "Error", null))
        var result = true
        if (eventLoading != SignUpUiEvents.Loading(false)) result = false
        if (eventToast != SignUpUiEvents.ShowToast("Invalid")) result = false
        eventLoading = null
        eventToast = null
        assert(result)
    }

    private fun dummyOtpOptions(): PhoneAuthOptions {
        return PhoneAuthOptions.newBuilder().build()
    }

    @Test
    fun `when event is OtpOption, set phone`() {
        val phone = "999999998"
        viewModel.onEvent(SignUpEvents.OtpOption(dummyOtpOptions(), null, phone))
        var result = true
        if(viewModel.state.value.phoneNo != phone) result = false
        assert(result)
    }

    @Test
    fun `when event is VerifyClick, emit loading` () {
        viewModel.onEvent(SignUpEvents.VerifyClick("123456", "user", "token"))
        var result = true
        if(eventLoading != SignUpUiEvents.Loading(true)) result = false
        eventLoading = null
        assert(result)
    }

    @Test
    fun `when event is ResendOtp, emit loading` () {
        viewModel.onEvent(SignUpEvents.ResendOtp(dummyOtpOptions()))
        var result = true
        if(eventLoading != SignUpUiEvents.Loading(true)) result = false
        eventLoading = null
        assert(result)
    }

    @Test
    fun `when event is WrongNo, set phone empty and emit enterPhoneStage` () {
        viewModel.onEvent(SignUpEvents.WrongNo)
        var result = true
        if(viewModel.state.value.phoneNo != "") result = false
        if(eventScreenChanged != SignUpUiEvents.ScreenChanged(SignUpProgress.EnterPhoneStage)) result = false
        eventScreenChanged = null
        assert(result)
    }
}